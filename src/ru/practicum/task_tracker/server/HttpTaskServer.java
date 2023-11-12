package ru.practicum.task_tracker.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.practicum.task_tracker.enumereits.Endpoint;


import ru.practicum.task_tracker.manager.Manager;
import ru.practicum.task_tracker.manager.TaskManager;
import ru.practicum.task_tracker.server.adapter.*;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

public class HttpTaskServer {

    private final static int PORT = 8080;
    private final static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    public HttpTaskServer()throws IOException{
        new KVServer().start();
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT),0);
        httpServer.createContext("/tasks",new TaskHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }



    static class TaskHandler implements HttpHandler {

        private final TaskManager httpManager = Manager.getDefaultHttp();
        private final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Task.class,new TaskSerializer())
                .registerTypeAdapter(Task.class,new TaskDeserialize())
                .registerTypeAdapter(Epic.class,new EpicSerializer())
                .registerTypeAdapter(Epic.class,new EpicDeserialize())
                .registerTypeAdapter(Subtask.class,new SubtaskSerializer())
                .registerTypeAdapter(Subtask.class,new SubtaskDeserialize())
                .create();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI path = exchange.getRequestURI();
            Endpoint endpoint = getEndpoint(path, exchange.getRequestMethod());

            switch (endpoint){
                case GET_HISTORY:
                    String responseHistory = gson.toJson(httpManager.getHistory());
                    writeResponse(exchange,responseHistory,200);
                    break;
                case GET_ID:
                    Task task =  httpManager.getTaskById(getTaskId(path).get());
                    if(task==null){
                        writeResponse(exchange,"Такого таска не существует",400);
                    }
                    String responseTaskId = gson.toJson(task);
                    writeResponse(exchange,responseTaskId,200);
                    break;

                case GET_EPIC:
                    String responseEpics = gson.toJson(httpManager.getEpics());
                    writeResponse(exchange,responseEpics,200);
                    break;

                case GET_TASK:
                        String responseTasks = gson.toJson(httpManager.getTasks());
                        writeResponse(exchange,responseTasks,200);

                case GET_TASKS:
                    String response =gson.toJson(httpManager.getPrioritizedTasks());
                    writeResponse(exchange,response,200);

                case GET_SUBTASK:
                    String responseSubtasks = gson.toJson(httpManager.getSubtasks());
                    writeResponse(exchange,responseSubtasks,200);

                case GET_EPIC_ID:
                    Epic epic =  httpManager.getEpicById(getTaskId(path).get());
                    if(epic==null){
                        writeResponse(exchange,"Такого эпика не существует",400);
                    }
                    String responseEpicId = gson.toJson(epic);
                    writeResponse(exchange,responseEpicId,200);
                    break;

                case GET_SUBTASK_ID:
                    Subtask subtask =  httpManager.getSubtaskById(getTaskId(path).get());
                    if(subtask==null){
                        writeResponse(exchange,"Такого сабтаска не существует",400);
                    }
                    String responseSubtaskId = gson.toJson(subtask);
                    writeResponse(exchange,responseSubtaskId,200);
                    break;

                case GET_SUBTASK_EPIC:
                    ArrayList<Subtask> subtaskArrayList = httpManager.gettingSubtaskFromEpic(getTaskId(path).get());
                    if(subtaskArrayList==null){
                        writeResponse(exchange,"Такого эпика не существует",400);
                    }
                    String responseSubtaskArr = gson.toJson(subtaskArrayList);
                    writeResponse(exchange,responseSubtaskArr,200);
                    break;

                case POST_EPIC:
                    handleEpic(exchange);
                    break;

                case POST_TASK:
                    handleTask(exchange);
                    break;

                case POST_SUBTASK:
                    handleSubtask(exchange);
                    break;

                case DELETE_TASK:
                    httpManager.deleteAllTask();
                    writeResponse(exchange,"Все таски были успешно удалены!",200);
                    break;

                case DELETE_EPIC_ID:
                    Epic epicDel = httpManager.getEpicById(getTaskId(path).get());
                    if(epicDel==null){
                        writeResponse(exchange,"Такого эпика не существует",400);
                    }
                    httpManager.deleteByIndexEpic(epicDel.getId());
                    writeResponse(exchange,"Эпик был успешно удален",200);
                    break;

                case DELETE_TASK_ID:
                    Task taskDel = httpManager.getTaskById(getTaskId(path).get());
                    if(taskDel==null){
                        writeResponse(exchange,"Такого таска не существует",400);
                    }
                    httpManager.deleteByIndexEpic(taskDel.getId());
                    writeResponse(exchange,"Таск был успешно удален",200);
                    break;

                case DELETE_SUBTASK_ID:
                    Subtask subtaskDel = httpManager.getSubtaskById(getTaskId(path).get());
                    if(subtaskDel==null){
                        writeResponse(exchange,"Такого сабтаска не существует",400);
                    }
                    httpManager.deleteByIndexEpic(subtaskDel.getId());
                    writeResponse(exchange,"Сабтаск был успешно удален",200);
                    break;

                case UNKNOWN:
                    break;

            }


        }
        public Endpoint getEndpoint(URI requestUri,String requestMethod) {
            String path = requestUri.getPath();
            String[] pathSplit = requestUri.getPath().split("/");
            String query = requestUri.getQuery();
            if(requestMethod.equals("GET")){
                    if(path.contains("history")){
                        return Endpoint.GET_HISTORY;
                    }
                    if(query==null&&pathSplit[pathSplit.length-1].equals("task")){
                        return Endpoint.GET_TASK;
                    }
                    if(query==null&&path.contains("epic")){
                        return Endpoint.GET_EPIC;
                    }
                    if(query==null&&path.contains("subtask")){
                        return Endpoint.GET_SUBTASK;
                    }


                if(pathSplit[pathSplit.length - 1].equals("task")) {
                    assert query != null;
                    if (query.contains("id")) {
                        return Endpoint.GET_ID;
                    }
                }
                if(pathSplit[pathSplit.length - 1].equals("epic")&&!pathSplit[pathSplit.length - 2].equals("subtask")) {
                    assert query != null;
                    if (query.contains("id")) {
                        return Endpoint.GET_EPIC_ID;
                    }
                }
                if(pathSplit[pathSplit.length - 1].equals("subtask")) {
                    assert query != null;
                    if (query.contains("id")) {
                        return Endpoint.GET_SUBTASK_ID;
                    }
                }
                if(path.contains("subtask") && path.contains("epic")) {
                    assert query != null;
                    if (query.contains("id")) {
                        return Endpoint.GET_SUBTASK_EPIC;
                    }
                }
                if(pathSplit[pathSplit.length-1].equals("tasks")){
                    return Endpoint.GET_TASKS;
                }

            }
            if(requestMethod.equals("POST")){
                if(pathSplit[pathSplit.length-1].equals("task")){
                    return Endpoint.POST_TASK;
                }
                if(path.contains("epic")){
                    return Endpoint.POST_EPIC;
                }
                if(path.contains("subtask")){
                    return Endpoint.POST_SUBTASK;
                }
            }
            if(requestMethod.equals("DELETE")){
                if(query==null&&pathSplit[pathSplit.length-1].equals("task")){
                    return Endpoint.DELETE_TASK;
                }
                assert query != null;
                if(query.contains("id")&&pathSplit[pathSplit.length-1].equals("task")){
                    return Endpoint.DELETE_TASK_ID;
                }
                if(path.contains("epic")&&query.contains("id")){
                    return Endpoint.DELETE_EPIC_ID;
                }
                if(path.contains("subtask")&&query.contains("id")){
                    return Endpoint.DELETE_SUBTASK_ID;
                }

            }
            return Endpoint.UNKNOWN;
        }
        private Optional<Long> getTaskId(URI path){
            try{
                String[] pathSplit = path.getQuery().split("=");
                return Optional.of(Long.parseLong(pathSplit[pathSplit.length-1]));
            }catch (NumberFormatException exp){
                return Optional.empty();
            }
        }
        private void writeResponse(HttpExchange exchange,
                                   String responseString,
                                   int responseCode) throws IOException {
            if(responseString.isBlank()) {
                exchange.sendResponseHeaders(responseCode, 0);
            } else {
                byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(responseCode, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        }


        private void handleEpic(HttpExchange exchange) throws IOException {

            InputStream taskIs = exchange.getRequestBody();
            String taskStr = new String(taskIs.readAllBytes(),DEFAULT_CHARSET);

            try {
                Epic epic = gson.fromJson(taskStr, Epic.class);
                if(epic.getName().isEmpty()&&epic.getId()==0){
                    writeResponse(exchange,"Name и id не может быть пустым",400);
                }
                if(httpManager.getEpics().containsKey(epic.getId())){
                    httpManager.updateEpic(epic);
                    writeResponse(exchange,"Сабтаск был обновлен",200);
                }
                httpManager.addNewEpic(epic);
                writeResponse(exchange,"Эпик был добавлен",200);
            }
            catch (IllegalArgumentException exception){
                writeResponse(exchange,"Возникла ошибка"+exception.getMessage(),400);
            }
            catch (JsonSyntaxException exception){
                writeResponse(exchange,"Получен некорректный JSON",400);
            }

        }
        private void handleSubtask(HttpExchange exchange) throws IOException {

            InputStream taskIs = exchange.getRequestBody();
            String taskStr = new String(taskIs.readAllBytes(),DEFAULT_CHARSET);

            try {
                Subtask subtask = gson.fromJson(taskStr, Subtask.class);
                if(subtask.getName().isEmpty()&&subtask.getId()==0){
                    writeResponse(exchange,"Name и id не может быть пустым",400);
                }
                if(httpManager.getSubtasks().containsKey(subtask.getId())){
                    httpManager.updateSubtask(subtask);
                    writeResponse(exchange,"Сабтаск был обновлен",200);
                }
                httpManager.addSubtask(subtask);
                writeResponse(exchange,"Сабтаск был добавлен",200);

            }
            catch (IllegalArgumentException exception){
                writeResponse(exchange,"Возникла ошибка: "+exception.getMessage(),400);
            }
            catch (JsonSyntaxException exception){
                writeResponse(exchange,"Получен некорректный JSON",400);
            }

        }
        private void handleTask(HttpExchange exchange) throws IOException {

            InputStream taskIs = exchange.getRequestBody();
            String taskStr = new String(taskIs.readAllBytes(),DEFAULT_CHARSET);

            try {
                Task task = gson.fromJson(taskStr, Task.class);
                if(task.getName().isEmpty()&&task.getId()<=0){
                    writeResponse(exchange,"Name и id не может быть пустым",400);
                }
                if(httpManager.getTasks().containsKey(task.getId())){
                    httpManager.updateTask(task);
                    writeResponse(exchange,"Таск был обновлен",200);
                }
                httpManager.addTask(task);
                writeResponse(exchange,"Таск был добавлен",200);
            }
            catch (IllegalArgumentException exception){
                writeResponse(exchange,"Возникла ошибка"+exception.getMessage(),400);
            }
            catch (JsonSyntaxException exception){
                writeResponse(exchange,"Получен некорректный JSON",400);
            }

        }






    }

}
