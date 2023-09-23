package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.File;

public class FileBackedTasksManagerTest {
    public static void main(String[] args){
        File file =new File("test.txt");
        FileBackedTasksManager tasksManager1 = new FileBackedTasksManager(file);

        Task task1  = new Task("Таск 1","Ну какой то таск");
        tasksManager1.addTask(task1);


        Epic epic1 =  new Epic("Эпик 1","Нужно сделать");
        long epic1Id  = tasksManager1.addNewEpic(epic1);
        Epic epic2 =  new Epic("Эпик 2","Нужно");
        long epic2Id  = tasksManager1.addNewEpic(epic2);
        Epic epic3 =  new Epic("Эпик 3","Нужно");
        long epic3Id  = tasksManager1.addNewEpic(epic3);


        Subtask subtask1 = new Subtask("Subtask1 создания","Написать что то",epic1Id);
        Long subtaskId1 = tasksManager1.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask2 создания","Написать что то",epic1Id);
        Long subtaskId2 = tasksManager1.addSubtask(subtask2);
        tasksManager1.getTaskById(1);
        tasksManager1.getEpicById(3);
        tasksManager1.getEpicById(4);

        FileBackedTasksManager tasksManager2 = FileBackedTasksManager.loadFromFile(file);

    }
}
