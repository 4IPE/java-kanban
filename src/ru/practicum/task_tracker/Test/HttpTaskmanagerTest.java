package ru.practicum.task_tracker.Test;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_tracker.manager.Manager;
import ru.practicum.task_tracker.manager.TaskManager;
import ru.practicum.task_tracker.server.HttpTaskManager;
import ru.practicum.task_tracker.server.KVServer;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.IOException;


public class HttpTaskmanagerTest{

    private TaskManager httpTaskManager;
    private  Task task1;
    private  Epic epic1;
    private   Subtask subtask1;
    @BeforeAll
    public static void beforeAll() throws IOException {
        new KVServer().start();

    }

    @BeforeEach
    public void beforeEach(){
        httpTaskManager = Manager.getDefault();
        task1  = new Task("Таск 1","Test","12.03.2022 22:23",20);
        epic1 =  new Epic("Эпик 1","Нужно сделать","13.01.2023 22:22",10);
        subtask1 = new Subtask("Subtask1 создания ","Написать что то ",epic1.getId(),"20.10.2026 20:00",10);
    }


    @Test
    public void addTest(){
        Throwable expTask = assertThrows(NullPointerException.class,()->httpTaskManager.addTask(null));
        assertEquals(NullPointerException.class,expTask.getClass(),"Неизвестная ошибка при работе с null у Таска"+expTask.getMessage());
        Throwable expEpic = assertThrows(NullPointerException.class,()->httpTaskManager.addNewEpic(null));
        assertEquals(NullPointerException.class,expEpic.getClass(),"Неизвестная ошибка при работе с null у Эпика"+expEpic.getMessage());
        Throwable expSubtask = assertThrows(NullPointerException.class,()->httpTaskManager.addSubtask(null));
        assertEquals(NullPointerException.class,expSubtask.getClass(),"Неизвестная ошибка при работе с null у Сабтаска"+expSubtask.getMessage());

        httpTaskManager.addTask(task1);
        httpTaskManager.addNewEpic(epic1);
        httpTaskManager.addSubtask(subtask1);

        assertEquals(task1,httpTaskManager.getTasks().get(task1.getId()),"Ошибка добавление таска");
        assertEquals(epic1,httpTaskManager.getEpics().get(epic1.getId()),"Ошибка добавление таска");
        assertEquals(subtask1,httpTaskManager.getSubtasks().get(subtask1.getId()),"Ошибка добавление таска");
    }


    @Test
    public void getTest(){
        httpTaskManager.addTask(task1);
        httpTaskManager.addNewEpic(epic1);
        httpTaskManager.addSubtask(subtask1);

        assertEquals(task1,httpTaskManager.getTaskById(task1.getId()));
        assertEquals(epic1,httpTaskManager.getEpicById(epic1.getId()));
        assertEquals(subtask1,httpTaskManager.getSubtaskById(subtask1.getId()));

       Throwable exceptionTask =assertThrows(RuntimeException.class,()->httpTaskManager.getTaskById(-1));
       assertEquals(RuntimeException.class,exceptionTask.getClass());
       Throwable exceptionEpic =assertThrows(RuntimeException.class,()->httpTaskManager.getEpicById(-1));
       assertEquals(RuntimeException.class,exceptionEpic.getClass());
       Throwable exceptionSubtask = assertThrows(RuntimeException.class,()->httpTaskManager.getEpicById(-1));
       assertEquals(RuntimeException.class,exceptionSubtask.getClass());
    }


    @Test
    public void updateTest(){
        Throwable exceptionTask = assertThrows(RuntimeException.class,()->httpTaskManager.updateTask(null));
        assertEquals(RuntimeException.class,exceptionTask.getClass(),"Ошибка не возникает в task");
        Throwable exceptionEpic = assertThrows(RuntimeException.class,()->httpTaskManager.updateEpic(null));
        assertEquals(RuntimeException.class,exceptionEpic.getClass(),"Ошибка не возникает в epic");
        Throwable exceptionSubtask = assertThrows(RuntimeException.class,()->httpTaskManager.updateSubtask(null));
        assertEquals(RuntimeException.class,exceptionSubtask.getClass(),"Ошибка не возникает в сабтаске");


        Epic epic2 = new Epic("Epic1","TEst",  "22.01.2022 22:22",10);
        Subtask subtask2 = new Subtask("Test","TEst",  epic1.getId(),"22.02.2022 22:22",10);
        Task task2 = new Task("Task1","TEst", "22.03.2022 22:22",10);
        httpTaskManager.addTask(task1);
        httpTaskManager.addNewEpic(epic1);
        httpTaskManager.addSubtask(subtask1);

        httpTaskManager.updateTask(task1);
        httpTaskManager.updateEpic(epic1);
        httpTaskManager.updateSubtask(subtask1);
        assertNotEquals(epic2,httpTaskManager.getTasks().get(task1.getId()));
        assertNotEquals(subtask2,httpTaskManager.getSubtasks().get(subtask1.getId()));
        assertNotEquals(task2,httpTaskManager.getEpics().get(epic1.getId()));
    }

}
