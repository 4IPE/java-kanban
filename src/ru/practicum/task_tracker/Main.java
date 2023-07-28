package ru.practicum.task_tracker;

import ru.practicum.task_tracker.manager.TaskTracker;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskTracker taskTracker = new TaskTracker();
        Task task1  = new Task("Таск 1","Ну какой то таск ");
        taskTracker.addTask(task1);


        Epic epic1 =  new Epic("Эпик 1","Нужно сделать");
        long epic1Id  = taskTracker.addNewEpic(epic1);
        Epic epic2 =  new Epic("Эпик 2  ","Нужно ");
        long epic2Id  = taskTracker.addNewEpic(epic2);
        Epic epic3 =  new Epic("Эпик 3 ","Нужно ");
        long epic3Id  = taskTracker.addNewEpic(epic3);

        Subtask subtask1 = new Subtask("Subtask1 создания ","Написать что то ","NEW",epic1Id);
        Long subtaskId1 = taskTracker.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask2 создания ","Написать что то ","IN_PROGRESS",epic1Id);
        Long subtaskId2 = taskTracker.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("Subtask3 создания ","Написать  ","DONE",epic2Id);
        Long subtaskId3 = taskTracker.addSubtask(subtask3);
        Subtask subtask4 = new Subtask("Subtask4 создания ","Написать  ","DONE",epic2Id);
        Long subtaskId4 = taskTracker.addSubtask(subtask4);
        Subtask subtask5 = new Subtask("Subtask5 создания ","Написать  ","NEW",epic3Id);
        Long subtaskId5 = taskTracker.addSubtask(subtask5);
        Subtask subtask6 = new Subtask("Subtask6 создания ","Написать  ","NEW",epic3Id);
        Long subtaskId6 = taskTracker.addSubtask(subtask6);


    }
}
