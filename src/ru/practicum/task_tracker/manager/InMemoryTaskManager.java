package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Comparator;


public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long,Subtask> subtasks = new HashMap<>();
    private final HashMap<Long,Task> tasks = new HashMap<>();
    private final HistoryManager historyManager = Manager.getDefaultHistoryManager();
    private Set<Task> prioritizedHistory = new TreeSet<>(new DateTimeComparatorTask());


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


    @Override
    public Set<Task> getPrioritizedTasks(){
        ArrayList<Task> historyArr = (ArrayList<Task>) getHistory();
        this.prioritizedHistory.addAll(historyArr);
        return this.prioritizedHistory;
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
        long id = epic.getId();
        epics.put(id,epic);
        calculationEndTime(epic);
        return id;
    }

    private void calculationEndTime(Task task){
        DateTimeSubtaskComparator dateTimeComparator = new DateTimeSubtaskComparator();
        if(task instanceof Epic){
            Epic epic = (Epic)task;
            if (!epic.getSubtaskIds().isEmpty()) {
                List<Subtask> sortSubtask = new ArrayList<>();
                int maxDuration  = 0;
               for(Long id :epic.getSubtaskIds()){
                   Subtask subtask =subtasks.get(id);
                   sortSubtask.add(subtask);
                   if(subtask.getDuration()>maxDuration){
                       maxDuration =subtask.getDuration();
                   }

               }
                sortSubtask.sort(dateTimeComparator);
                epic.setStartTime(sortSubtask.get(0).getStartTime());
                epic.setEndTime(sortSubtask.get(0).getEndTime().plus(maxDuration,ChronoUnit.MINUTES));
                epic.setDuration(maxDuration);
                return;

            }
            else {
                epic.setEndTime(epic.getStartTime().plus(epic.getDuration(),ChronoUnit.MINUTES));
                return;

            }

        }
        if (task != null) {
            task.setEndTime(task.getStartTime().plus(task.getDuration(), ChronoUnit.MINUTES));
        }

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
        calculationEndTime(subtask);
        calculationEndTime(epic);
        return id;
    }
    @Override
    public Long addTask(Task task){
        long id = task.getId();
        tasks.put(id,task);
        calculationEndTime(task);
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
        calculationEndTime(epics.get(subtask.getEpicId()));
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
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            subtasks.remove(id);
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

    //COMPARATORS
    static class DateTimeSubtaskComparator implements Comparator<Subtask> {

        @Override
        public int compare(Subtask task1, Subtask task2) {

            // сравниваем товары — более дорогой должен быть дальше в списке
            if (task1.getStartTime().isBefore(task2.getStartTime())) {
                return 1;

                // более дешёвый — ближе к началу списка
            } else if (task1.getStartTime().isAfter(task2.getStartTime())) {
                return -1;

                // если стоимость равна, нужно вернуть 0
            } else {
                return 0;
            }
        }
    }
    static class DateTimeComparatorTask implements Comparator<Task> {

        @Override
        public int compare(Task task1, Task task2) {


            if (task1.getStartTime().isBefore(task2.getStartTime())) {
                return 1;


            } else if (task1.getStartTime().isAfter(task2.getStartTime())) {
                return -1;


            } else {
                return 0;
            }
        }
    }

}

