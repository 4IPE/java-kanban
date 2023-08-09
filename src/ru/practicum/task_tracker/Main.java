package ru.practicum.task_tracker;

import ru.practicum.task_tracker.manager.Manager;
import ru.practicum.task_tracker.manager.TaskManager;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Manager.getDefault();
        Task task1  = new Task("Таск 1","Ну какой то таск ");
        taskManager.addTask(task1);


        Epic epic1 =  new Epic("Эпик 1","Нужно сделать");
        long epic1Id  = taskManager.addNewEpic(epic1);
        Epic epic2 =  new Epic("Эпик 2  ","Нужно ");
        long epic2Id  = taskManager.addNewEpic(epic2);
        Epic epic3 =  new Epic("Эпик 3 ","Нужно ");
        long epic3Id  = taskManager.addNewEpic(epic3);

        Subtask subtask1 = new Subtask("Subtask1 создания ","Написать что то ",epic1Id);
        Long subtaskId1 = taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask2 создания ","Написать что то ",epic1Id);
        Long subtaskId2 = taskManager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("Subtask3 создания ","Написать  ",epic2Id);
        Long subtaskId3 = taskManager.addSubtask(subtask3);
        Subtask subtask4 = new Subtask("Subtask4 создания ","Написать  ",epic2Id);
        Long subtaskId4 = taskManager.addSubtask(subtask4);
        Subtask subtask5 = new Subtask("Subtask5 создания ","Написать  ",epic3Id);
        Long subtaskId5 = taskManager.addSubtask(subtask5);
        Subtask subtask6 = new Subtask("Subtask6 создания ","Написать  ",epic3Id);
        Long subtaskId6 = taskManager.addSubtask(subtask6);



    }
}
