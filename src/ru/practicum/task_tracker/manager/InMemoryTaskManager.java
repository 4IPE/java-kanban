package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long,Subtask> subtasks = new HashMap<>();
    private final HashMap<Long,Task> tasks = new HashMap<>();
    private final HistoryManager historyManager = Manager.getDefaultHistoryManager();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Long addNewEpic(Epic epic){
        long id = epic.getId();
        epics.put(id,epic);

        return id;
    }
    @Override
    public Long addSubtask(Subtask subtask){
        Epic epic = epics.get(subtask.getEpicId());
        if (epics==null){
            return null;
        }

        long id = subtask.getId();
        subtasks.put(id,subtask);
        epic.addSubtaskId(subtask.getId());
        updateStatusEpic(subtask.getEpicId());

        return id;
    }
    @Override
    public Long addTask(Task task){
        long id = task.getId();
        tasks.put(id,task);

        return id;
    }

    @Override
    public void updateTask(Task task){
        Task savedTask = tasks.get(task.getId());
        if(savedTask==null){
            return;
        }
        tasks.put(task.getId(),task);
    }
    @Override
    public void updateSubtask(Subtask subtask){
        Subtask savedSubtask = subtasks.get(subtask.getId());
        if(savedSubtask==null){
            return;
        }
        subtasks.put(subtask.getId(),subtask);
        updateStatusEpic(subtask.getEpicId());
    }
    @Override
    public void updateEpic(Epic epic){
        Epic savedEpic = epics.get(epic.getId());
        if(savedEpic==null){
            return;
        }
        epics.put(epic.getId(),epic);
    }
    private void updateStatusEpic(long epicId){
        Epic epic = epics.get(epicId);
        ArrayList<Long> subtaskIds = epic.getSubtaskIds();
        if(subtaskIds.isEmpty()){
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        TaskStatus status = null;
        for (long subtaskId: subtaskIds){
            Subtask subtask = subtasks.get(subtaskId);

            //Первый проход
            if (status==null){
                status = subtask.getStatus();
                continue;
            }
            if (status==subtask.getStatus()
                    && status!=TaskStatus.IN_PROGRESS){
                continue;
            }

            epic.setStatus(TaskStatus.IN_PROGRESS);
            return;
        }
        epic.setStatus(status);

    }
    @Override
    public Task getTaskById(long id){
        historyManager.addTask(tasks.get(id));
        return tasks.get(id);

    }
    @Override
    public Epic getEpicById(long id){
        historyManager.addTask(epics.get(id));
        return epics.get(id);
    }
    @Override
    public Subtask getSubtaskById(long id){
        historyManager.addTask(subtasks.get(id));
        return subtasks.get(id);
    }
    @Override
    public ArrayList<Subtask> gettingSubtaskFromEpic(long epicId){
        Epic epic = epics.get(epicId);
        if (epic==null){
            return null;
        }
        ArrayList<Long> subtaskIds = epic.getSubtaskIds();
        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        for (Long subtaskId:subtaskIds){
            subtaskArrayList.add(subtasks.get(subtaskId));
        }
        historyManager.addTask(epics.get(epicId));
        return subtaskArrayList;
    }
    @Override
    public void deleteAllTask(){
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }
    @Override
    public void deleteByIndexTask(long id ){
        if(tasks.get(id)!=null) {
            tasks.remove(id);
        }
        else{
            System.out.println("Такой задачи ,под таким номером не существует");
        }
    }
    @Override
    public void deleteByIndexEpic(long id ){
        if(epics.get(id)!=null) {
            epics.remove(id);
        }
        else{
            System.out.println("Такой задачи ,под таким номером не существует");
        }
    }
    @Override
    public void deleteByIndexSubtask(long id ){
        if(subtasks.get(id)!=null) {
            subtasks.remove(id);
        }
        else{
            System.out.println("Такой задачи ,под таким номером не существует");
        }
    }

    @Override
    public void printAllTask(){
        if (!tasks.isEmpty()) {
            System.out.println(tasks);
        }
        if (!epics.isEmpty()) {
            System.out.println(epics);
        }
        if (!subtasks.isEmpty()) {
            System.out.println(subtasks);
        }
        else{
            System.out.println("Нету задач");
        }


    }

}

