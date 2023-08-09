package ru.practicum.task_tracker.manager;

public class Manager {

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager(){
        return new InMemoryHistoryManager();
    }
}
