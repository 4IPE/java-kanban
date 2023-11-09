package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.server.HttpTaskManager;



public class Manager {

    public static TaskManager getDefault(){
        return new HttpTaskManager("http://localhost:8080");
    }

    public static HistoryManager getDefaultHistoryManager(){
        return new InMemoryHistoryManager();
    }

}
