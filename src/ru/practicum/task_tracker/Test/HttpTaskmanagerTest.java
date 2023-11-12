package ru.practicum.task_tracker.Test;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_tracker.manager.Manager;
import ru.practicum.task_tracker.server.KVServer;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.io.IOException;


public class HttpTaskmanagerTest extends TaskManagerTest{


    @BeforeAll
    public static  void beforeAll(){
        try {
            new KVServer().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        epic =  new Epic("Test","TEst","22.01.2022 22:22",10);
        task = new Task("Test","TEst",  "22.02.2022 22:22",1);
        subtask = new Subtask("Test","TEst",  epic.getId(),"22.03.2022 22:22",10);
        epic1 = new Epic("Test","TEst","22.04.2022 22:22",10);
        task1 = new Task("Test","TEst",  "22.02.2022 22:23",1);
        subtask1 = new Subtask("Test","TEst",  epic.getId(),"22.03.2022 22:23",1);

    }


    @BeforeEach
    public void beforeEach(){
        taskManager = Manager.getDefaultHttp();
    }


    @Test
    public void addTest(){
        Throwable expTask = assertThrows(NullPointerException.class,()->taskManager.addTask(null));
        assertEquals(NullPointerException.class,expTask.getClass(),"Неизвестная ошибка при работе с null у Таска"+expTask.getMessage());
        Throwable expEpic = assertThrows(NullPointerException.class,()->taskManager.addNewEpic(null));
        assertEquals(NullPointerException.class,expEpic.getClass(),"Неизвестная ошибка при работе с null у Эпика"+expEpic.getMessage());
        Throwable expSubtask = assertThrows(NullPointerException.class,()->taskManager.addSubtask(null));
        assertEquals(NullPointerException.class,expSubtask.getClass(),"Неизвестная ошибка при работе с null у Сабтаска"+expSubtask.getMessage());

        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);






        assertEquals(task,taskManager.getTasks().get(task.getId()),"Ошибка добавление таска");
        assertEquals(epic,taskManager.getEpics().get(epic.getId()),"Ошибка добавление таска");
        assertEquals(subtask,taskManager.getSubtasks().get(subtask.getId()),"Ошибка добавление таска");
    }


    @Test
    public void getTest(){
        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);



        assertEquals(task,taskManager.getTaskById(task.getId()));
        assertEquals(epic,taskManager.getEpicById(epic.getId()));
        assertEquals(subtask,taskManager.getSubtaskById(subtask.getId()));

       Throwable exceptionTask =assertThrows(RuntimeException.class,()->taskManager.getTaskById(-1));
       assertEquals(RuntimeException.class,exceptionTask.getClass());
       Throwable exceptionEpic =assertThrows(RuntimeException.class,()->taskManager.getEpicById(-1));
       assertEquals(RuntimeException.class,exceptionEpic.getClass());
       Throwable exceptionSubtask = assertThrows(RuntimeException.class,()->taskManager.getEpicById(-1));
       assertEquals(RuntimeException.class,exceptionSubtask.getClass());
    }

    @Override
    @Test
    public void getByIDTest() {
        Throwable expTask = assertThrows(RuntimeException.class,()->taskManager.getTaskById(-1));
        assertEquals(RuntimeException.class,expTask.getClass(),"Неизвестная ошибка при работе с id=-1 у Таска"+expTask.getMessage());
        Throwable expEpic = assertThrows(RuntimeException.class,()->taskManager.getEpicById(-1));
        assertEquals(RuntimeException.class,expEpic.getClass(),"Неизвестная ошибка при работе с id=-1 у Эпика"+expEpic.getMessage());
        Throwable expSubtask = assertThrows(RuntimeException.class,()->taskManager.getSubtaskById(-1));
        assertEquals(RuntimeException.class,expSubtask.getClass(),"Неизвестная ошибка при работе с id=-1 у Сабтаска"+expSubtask.getMessage());
        Throwable expSubtaskEpic = assertThrows(RuntimeException.class,()->taskManager.gettingSubtaskFromEpic(-1));
        assertEquals(RuntimeException.class,expSubtaskEpic.getClass(),"Неизвестная ошибка при работе с id=-1 "+expSubtask.getMessage());

        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(task,taskManager.getTaskById(task.getId()),"Не получает существующий task");
        assertEquals(subtask,taskManager.getSubtaskById(subtask.getId()),"Не получает существующий subtask");
        assertEquals(epic,taskManager.getEpicById(epic.getId()),"Не получает существующий epic");
        assertEquals(subtask,taskManager.gettingSubtaskFromEpic(epic.getId()).get(0) , "Не получает существующий subtask по id epic ");

    }

}
