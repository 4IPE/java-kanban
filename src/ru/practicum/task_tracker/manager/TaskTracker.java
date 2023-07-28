package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskTracker {
   private HashMap<Long, Epic> epics = new HashMap<>();
   private HashMap<Long,Subtask> subtasks = new HashMap<>();
   private HashMap<Long,Task> tasks = new HashMap<>();



    public long addNewEpic(Epic epic){
         long id = epic.getId();
         epics.put(id,epic);

         return id;
    }
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

    public Long addTask(Task task){
        long id = task.getId();
        tasks.put(id,task);

        return id;
    }


    public void updateTask(Task task){
        Task savedTask = tasks.get(task.getId());
        if(savedTask==null){
            return;
        }
        tasks.put(task.getId(),task);
    }

    public void updateSubtask(Subtask subtask){
        Subtask savedSubtask = subtasks.get(subtask.getId());
        if(savedSubtask==null){
            return;
        }
        subtasks.put(subtask.getId(),subtask);
        updateStatusEpic(subtask.getEpicId());
    }
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
            epic.setStatus("NEW");
            return;
        }

        String status = null;
        for (long subtaskId: subtaskIds){
            Subtask subtask = subtasks.get(subtaskId);

            //Первый проход
            if (status==null){
                status = subtask.getStatus();
                continue;
            }
            if (status.equals(subtask.getStatus())
                    && !status.equals("IN_PROGRESS")){
                continue;
            }

            epic.setStatus("IN_PROGRESS");
            return;
        }
        epic.setStatus(status);

    }

    public Task gettingByIndexTask(long id){
            return tasks.get(id);

    }
    public Epic gettingByIndexEpic(long id){

        return epics.get(id);
    }
    public Subtask gettingByIndexSubtask(long id){

        return subtasks.get(id);
    }

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
        return subtaskArrayList;
    }

    public void deleteAllTask(){
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    public void deleteByIndexTask(long id ){
    if(tasks.get(id)!=null) {
        tasks.remove(id);
    }
    else{
        System.out.println("Такой задачи ,под таким номером не существует");
    }
    }
    public void deleteByIndexEpic(long id ){
        if(epics.get(id)!=null) {
            epics.remove(id);
        }
        else{
            System.out.println("Такой задачи ,под таким номером не существует");
        }
    }
    public void deleteByIndexSubtask(long id ){
        if(subtasks.get(id)!=null) {
            subtasks.remove(id);
        }
        else{
            System.out.println("Такой задачи ,под таким номером не существует");
        }
    }


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
