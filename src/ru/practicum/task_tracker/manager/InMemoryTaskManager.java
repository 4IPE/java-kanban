package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Comparator;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long,Subtask> subtasks = new HashMap<>();
    private final HashMap<Long,Task> tasks = new HashMap<>();
    private final HistoryManager historyManager = Manager.getDefaultHistoryManager();
    private  Set<Task>  prioritized ;
    private final ArrayList<LocalDateTime> dateTimesTasks = new ArrayList<>();


    public List<Epic> getEpicsVal() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getSubtasksVal() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Task> getTasksVal() {
        return new ArrayList<>(tasks.values());
    }


    @Override
    public HashMap<Long, Epic> getEpics() {
        return epics;
    }
    @Override
    public HashMap<Long, Subtask> getSubtasks() {
        return subtasks;
    }
    @Override
    public HashMap<Long, Task> getTasks() {
        return tasks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryTaskManager that = (InMemoryTaskManager) o;
        return Objects.equals(epics, that.epics) && Objects.equals(subtasks, that.subtasks) && Objects.equals(tasks, that.tasks);
    }


    private void addAllTaskInPrioritizedArr(){
        prioritized = new TreeSet<>(new DateTimeComparatorTask());
        ArrayList<Task> allTaskList = new ArrayList<>();
        allTaskList.addAll(tasks.values());
        allTaskList.addAll(epics.values());
        allTaskList.addAll(subtasks.values());
        prioritized.addAll(allTaskList);
    }


    @Override
    public Set<Task> getPrioritizedTasks(){
        return prioritized;
    }


    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Long addNewEpic(Epic epic){
        if(!dateTimesTasks.contains(epic.getStartTime())) {
            long id = epic.getId();
            epics.put(id, epic);
            calculationEndTime(epic);
            if(epic.getStartTime()!=null) {
                dateTimesTasks.add(epic.getStartTime());
            }
            addAllTaskInPrioritizedArr();
            return id;
        }
        else {
            throw  new IllegalArgumentException("Нельзя добавлять задачи с одним стартовым временем");
        }
    }

    private void calculationEndTime(Task task){
        if(task instanceof Epic){
            DateTimeSubtaskComparator dateTimeComparator = new DateTimeSubtaskComparator();
            Epic epic = (Epic)task;
            if (!epic.getSubtaskIds().isEmpty()) {
                List<Subtask> sortSubtask = new ArrayList<>();
                int maxDuration  = 0;
               for(Long id :epic.getSubtaskIds()){
                   if(subtasks.get(id)==null){
                       continue;
                   }
                   Subtask subtask =subtasks.get(id);
                   sortSubtask.add(subtask);
                   if(subtask.getDuration()>maxDuration){
                       maxDuration =subtask.getDuration();
                   }

               }
               if(!sortSubtask.isEmpty()) {
                   sortSubtask.sort(dateTimeComparator);
                   if (sortSubtask.get(0).getStartTime() != null) {
                       epic.setStartTime(sortSubtask.get(0).getStartTime());
                   }
                   if (sortSubtask.get(0).getEndTime() != null) {
                       epic.setEndTime(sortSubtask.get(0).getEndTime().plus(maxDuration, ChronoUnit.MINUTES));
                   }
                   epic.setDuration(maxDuration);
               }
                return;

            }
            else {
                if(epic.getStartTime()!=null) {
                    epic.setEndTime(epic.getStartTime().plus(epic.getDuration(), ChronoUnit.MINUTES));
                }
                else {
                    epic.setEndTime(null);
                }
                return;

            }

        }
        if (task.getStartTime()==null) {
            task.setEndTime(null);
        }
        else {
            task.setEndTime(task.getStartTime().plus(task.getDuration(), ChronoUnit.MINUTES));

        }

    }
    @Override
    public Long addSubtask(Subtask subtask){
        Epic epic = epics.get(subtask.getEpicId());
        if (epic==null){
            return null;
        }

        dateTimesTasks.remove(epic.getStartTime());

        if(!dateTimesTasks.contains(subtask.getStartTime())) {
            long id = subtask.getId();
            subtasks.put(id,subtask);
            epic.addSubtaskId(subtask.getId());
            updateStatusEpic(subtask.getEpicId());
            calculationEndTime(subtask);
            calculationEndTime(epic);

            if(subtask.getStartTime()!=null && epic.getStartTime()!=null) {
                dateTimesTasks.add(subtask.getStartTime());
            }
            addAllTaskInPrioritizedArr();
            return id;
        }
        else {
            throw  new IllegalArgumentException("Нельзя добавлять задачи с одним стартовым временем");
        }
    }
    @Override
    public Long addTask(Task task){
        if(!dateTimesTasks.contains(task.getStartTime())){
            long id = task.getId();
            tasks.put(id,task);
            calculationEndTime(task);
            if(task.getStartTime()!=null) {
                dateTimesTasks.add(task.getStartTime());
            }
            addAllTaskInPrioritizedArr();
            return id;
        }
        else {
            throw  new IllegalArgumentException("Нельзя добавлять задачи с одним стартовым временем");
        }
    }

    @Override
    public void updateTask(Task task){
        Task savedTask = tasks.get(task.getId());
        if(savedTask==null){
            return;
        }
        dateTimesTasks.remove(savedTask.getStartTime());
        if(!dateTimesTasks.contains(task.getStartTime())) {
            tasks.put(task.getId(),task);
            calculationEndTime(task);

            if(task.getStartTime()!=null) {
                dateTimesTasks.add(task.getStartTime());
            }
            addAllTaskInPrioritizedArr();
        }
        else {
            throw  new IllegalArgumentException("Нельзя добавлять задачи с одним стартовым временем");
        }


    }
    @Override
    public void updateSubtask(Subtask subtask){
        Subtask savedSubtask = subtasks.get(subtask.getId());
        if(savedSubtask==null){
            return;
        }
        dateTimesTasks.remove(savedSubtask.getStartTime());
        if(!dateTimesTasks.contains(subtask.getStartTime())) {
            subtasks.put(subtask.getId(),subtask);
            updateStatusEpic(subtask.getEpicId());
            calculationEndTime(epics.get(subtask.getEpicId()));
            calculationEndTime(subtask);

            if(subtask.getStartTime()!=null && epics.get(subtask.getEpicId())!=null) {
                dateTimesTasks.add(subtask.getStartTime());
            }
            addAllTaskInPrioritizedArr();
        }
        else {
            throw  new IllegalArgumentException("Нельзя добавлять задачи с одним стартовым временем");
        }

    }
    @Override
    public void updateEpic(Epic epic){
        Epic savedEpic = epics.get(epic.getId());
        if(savedEpic==null){
            return;
        }

        dateTimesTasks.remove(savedEpic.getStartTime());
        if(!dateTimesTasks.contains(epic.getStartTime())) {
            epics.put(epic.getId(),epic);
            calculationEndTime(epic);
            if(epic.getStartTime()!=null) {
                dateTimesTasks.add(epic.getStartTime());
            }
            addAllTaskInPrioritizedArr();
        }
        else {
            throw  new IllegalArgumentException("Нельзя добавлять задачи с одним стартовым временем");
        }

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
        dateTimesTasks.clear();
        prioritized.clear();
    }
    @Override
    public void deleteByIndexTask(long id ){
        if(tasks.get(id)!=null) {
            dateTimesTasks.remove(tasks.get(id).getStartTime());
            tasks.remove(id);
        }
        else{
            System.out.println("Такой задачи ,под таким номером не существует");
        }
    }
    @Override
    public void deleteByIndexEpic(long id ){
        if(epics.get(id)!=null) {
            dateTimesTasks.remove(epics.get(id).getStartTime());
            epics.remove(id);
        }
        else{
            System.out.println("Такой задачи ,под таким номером не существует");
        }
    }
    @Override
    public void deleteByIndexSubtask(long id ){
        if(subtasks.get(id)!=null) {
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            dateTimesTasks.remove(subtasks.get(id).getStartTime());
            subtasks.remove(id);
            epic.removeSubtaskId(id);
            calculationEndTime(epic);
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

    static class DateTimeSubtaskComparator implements Comparator<Subtask> {

        @Override
        public int compare(Subtask task1, Subtask task2) {

            if (task1.getStartTime().isBefore(task2.getStartTime())) {
                return 1;


            } else if (task1.getStartTime().isAfter(task2.getStartTime())) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }
    static class DateTimeComparatorTask implements Comparator<Task> {

        @Override
        public int compare(Task task1, Task task2) {

            if(task1.getStartTime() == null || task2.getStartTime()==null) {
                return 1;
            }
            if (task1.getStartTime().isBefore(task2.getStartTime())) {
                return 1;


            } else if (task1.getStartTime().isAfter(task2.getStartTime())) {
                return -1;

            }
            else {
                return 0;
            }
        }
    }

}

