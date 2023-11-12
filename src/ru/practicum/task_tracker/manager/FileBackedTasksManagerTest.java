package ru.practicum.task_tracker.manager;



import ru.practicum.task_tracker.server.HttpTaskServer;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.IOException;


public class FileBackedTasksManagerTest {
    public static void main(String[] args) throws IOException {
        String fileName ="task.txt";
        FileBackedTasksManager taskManager = new FileBackedTasksManager(fileName);
        Task task1  = new Task("Таск 1","Ну какой то таск ","12.03.2022 22:22",20);
        taskManager.addTask(task1);
        Task task2  = new Task("Таск 2","Ну какой то таск ","13.03.2023 22:22",20);
        taskManager.addTask(task2);


        Epic epic1 =  new Epic("Эпик 1","Нужно сделать","13.01.2023 22:22",10);
        long epic1Id  = taskManager.addNewEpic(epic1);
        Epic epic2 =  new Epic("Эпик 2  ","Нужно ","15.10.2022 22:22",30);
        long epic2Id  = taskManager.addNewEpic(epic2);
        Epic epic3 =  new Epic("Эпик 3 ","Нужно ","11.09.2023 22:22",60);
        long epic3Id  = taskManager.addNewEpic(epic3);

        Subtask subtask1 = new Subtask("Subtask1 создания ","Написать что то ",epic1Id,"22.12.2023 22:22",10);
        Long subtaskId1 = taskManager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask2 создания ","Написать что то ",epic1Id,"11.03.2012 22:22",20);
        Long subtaskId2 = taskManager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("Subtask3 создания ","Написать  ",epic2Id,"22.02.2023 22:22",20);
        Long subtaskId3 = taskManager.addSubtask(subtask3);
        Subtask subtask4 = new Subtask("Subtask4 создания ","Написать  ",epic2Id,"21.02.2022 22:22",20);
        Long subtaskId4 = taskManager.addSubtask(subtask4);
        Subtask subtask5 = new Subtask("Subtask5 создания ","Написать  ",epic3Id,"03.03.2022 22:22",10);
        Long subtaskId5 = taskManager.addSubtask(subtask5);
        Subtask subtask6 = new Subtask("Subtask6 создания ","Написать  ",epic3Id,"12.02.2023 22:22",40);
        Long subtaskId6 = taskManager.addSubtask(subtask6);

        taskManager.getTaskById(1);
        taskManager.getEpicById(3);
        taskManager.getEpicById(4);

        HttpTaskServer server = new HttpTaskServer();

    }
}
