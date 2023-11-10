package ru.practicum.task_tracker.server;

import com.google.gson.*;
import ru.practicum.task_tracker.server.adapter.*;
import ru.practicum.task_tracker.manager.FileBackedTasksManager;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.net.URI;
import java.util.ArrayList;

public class HttpTaskManager extends FileBackedTasksManager {

    private final URI uri;
    private final KVTaskClient kvTaskClient;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Task.class,new TaskSerializer())
            .registerTypeAdapter(Task.class,new TaskDeserialize())
            .registerTypeAdapter(Epic.class,new EpicSerializer())
            .registerTypeAdapter(Epic.class,new EpicDeserialize())
            .registerTypeAdapter(Subtask.class,new SubtaskSerializer())
            .registerTypeAdapter(Subtask.class,new SubtaskDeserialize())
            .create();
    public HttpTaskManager(String uri)  {
        super(uri);

        try {
            this.uri = new URI(uri);
            this.kvTaskClient =new KVTaskClient(uri);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Long addNewEpic(Epic epic) {
        Long id =super.addNewEpic(epic);
        try {
            kvTaskClient.put(String.valueOf(id),gson.toJson(getEpics().get(id)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    @Override
    public Long addSubtask(Subtask subtask){
        Long id =super.addSubtask(subtask);
        try {
            kvTaskClient.put(String.valueOf(id),gson.toJson(subtask));
            updateEpic(getEpics().get(subtask.getEpicId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return id;
    }
    @Override
    public Long addTask(Task task){
        Long id = super.addTask(task);
        try {
            kvTaskClient.put(String.valueOf(id),gson.toJson(getTasks().get(id)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    @Override
    public Task getTaskById(long id) {
        try {
           return gson.fromJson(kvTaskClient.load(String.valueOf(id)),Task.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Epic getEpicById(long id) {
        try {
            return gson.fromJson(kvTaskClient.load(String.valueOf(id)),Epic.class);
        } catch (JsonSyntaxException e){
            throw new JsonSyntaxException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Subtask getSubtaskById(long id) {
        try {
           return gson.fromJson(kvTaskClient.load(String.valueOf(id)),Subtask.class);
        }  catch (JsonSyntaxException e){
            throw new JsonSyntaxException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<Subtask> gettingSubtaskFromEpic(long epicId) {
        try {
            ArrayList<Long> arrayListId = gson.fromJson(kvTaskClient.load(String.valueOf(epicId)),Epic.class).getSubtaskIds();
            ArrayList<Subtask> subtasks = new ArrayList<>();
            assert !arrayListId.isEmpty();
            for (Long id :arrayListId){
                subtasks.add(getSubtaskById(id));
            }
            return subtasks;
        } catch (JsonSyntaxException e){
            throw new JsonSyntaxException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateTask(Task task) {
        try {
            kvTaskClient.put(String.valueOf(task.getId()),gson.toJson(task));
        }  catch (JsonSyntaxException e){
            throw new JsonSyntaxException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        try {
            kvTaskClient.put(String.valueOf(subtask.getId()),gson.toJson(subtask));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        try {
            kvTaskClient.put(String.valueOf(epic.getId()),gson.toJson(epic));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
