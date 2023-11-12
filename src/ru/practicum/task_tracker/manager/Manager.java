package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.server.HttpTaskManager;



public class Manager {

    public static TaskManager getDefaultHttp(){
        return new HttpTaskManager("http://localhost:8078");
    }
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager(){
        return new InMemoryHistoryManager();
    }

}
