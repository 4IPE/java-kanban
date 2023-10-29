package ru.practicum.task_tracker.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.manager.Manager;
import ru.practicum.task_tracker.manager.TaskManager;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.time.temporal.ChronoUnit;


public abstract class  TaskManagerTest {

    protected  Epic epic;
    protected  Epic epic1;
    protected  Subtask subtask;
    protected  Task task;
    protected TaskManager taskManager;

    @BeforeEach
    public void beforeEach(){
        taskManager = Manager.getDefault();
        epic =  new Epic("Test","TEst","22.01.2022 22:22",10);
        task = new Task("Test","TEst",  "22.02.2022 22:22",1);
        subtask = new Subtask("Test","TEst",  epic.getId(),"22.03.2022 22:22",10);
        epic1 = new Epic("Test","TEst","22.04.2022 22:22",10);
    }



    //Работает
    @Test
    public void addTest(){
        Throwable exceptionTask = assertThrows(NullPointerException.class,()->taskManager.addTask(null));
        assertEquals(NullPointerException.class,exceptionTask.getClass(),"Ошибка не возникает в task");
        Throwable exceptionEpic = assertThrows(NullPointerException.class,()->taskManager.addNewEpic(null));
        assertEquals(NullPointerException.class,exceptionEpic.getClass(),"Ошибка не возникает в epic");
        Throwable exceptionSubtask = assertThrows(NullPointerException.class,()->taskManager.addSubtask(null));
        assertEquals(NullPointerException.class,exceptionSubtask.getClass(),"Ошибка не возникает в сабтаске");

        assertEquals(0,taskManager.getEpics().size(),"Список эпиков не пустой ");
        assertEquals(0,taskManager.getTasks().size(),"Список тасков не пустой ");
        assertEquals(0,taskManager.getSubtasks().size(),"Список cабтасков не пустой ");

        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(1,taskManager.getEpics().size(),"Список эпиков  пустой ");
        assertEquals(1,taskManager.getTasks().size(),"Список тасков  пустой ");
        assertEquals(1,taskManager.getSubtasks().size(),"Список cабтасков  пустой ");
    }
    //Работает
    @Test
    public void calculationEndTime(){
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.addTask(task);
        assertEquals(subtask.getEndTime().plus(subtask.getDuration(), ChronoUnit.MINUTES),epic.getEndTime(),"Ошибка с эпиком в конечном времени");
        assertEquals(subtask.getStartTime(),epic.getStartTime(),"Ошибка с эпиком в стартовом времени");
        assertEquals(task.getStartTime().plus(task.getDuration(),ChronoUnit.MINUTES),task.getEndTime(),"Ошибка с таском");
    }

    // Работает
    @Test
    public void updateTest(){
        Throwable exceptionTask = assertThrows(NullPointerException.class,()->taskManager.updateTask(null));
        assertEquals(NullPointerException.class,exceptionTask.getClass(),"Ошибка не возникает в task");
        Throwable exceptionEpic = assertThrows(NullPointerException.class,()->taskManager.updateEpic(null));
        assertEquals(NullPointerException.class,exceptionEpic.getClass(),"Ошибка не возникает в epic");
        Throwable exceptionSubtask = assertThrows(NullPointerException.class,()->taskManager.updateSubtask(null));
        assertEquals(NullPointerException.class,exceptionSubtask.getClass(),"Ошибка не возникает в сабтаске");


        Epic epic1 = new Epic("Epic1","TEst",  "22.01.2022 22:22",10);
        Subtask subtask1 = new Subtask("Test","TEst",  epic1.getId(),"22.02.2022 22:22",10);
        Task task1 = new Task("Task1","TEst", "22.03.2022 22:22",10);
        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);

        taskManager.updateTask(task1);
        taskManager.updateEpic(epic1);
        taskManager.updateSubtask(subtask1);
        assertNotEquals(task,taskManager.getTasks().get(task1.getId()));
        assertNotEquals(subtask,taskManager.getSubtasks().get(subtask1.getId()));
        assertNotEquals(epic,taskManager.getEpics().get(epic1.getId()));

    }
    //Работает
    @Test
    public void updateStatusEpic(){
        assertEquals(epic.getStatus(), TaskStatus.NEW);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);
        assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus());
    }
//Работает
    @Test
    public void deleteByIndexTest(){
        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);

        taskManager.deleteByIndexTask(-1);
        taskManager.deleteByIndexEpic(-1);
        taskManager.deleteByIndexSubtask(-1);
        assertEquals(1,taskManager.getTasks().size(),"Ошибка удаления тасков");
        assertEquals(1,taskManager.getEpics().size(),"Ошибка удаления эпиков");
        assertEquals(1,taskManager.getSubtasks().size(),"Ошибка удаления сабтасков");

        taskManager.deleteByIndexTask(task.getId());
        taskManager.deleteByIndexSubtask(subtask.getId());
        taskManager.deleteByIndexEpic(epic.getId());

        assertEquals(0,taskManager.getTasks().size(),"Ошибка удаления тасков");
        assertEquals(0,taskManager.getEpics().size(),"Ошибка удаления эпиков");
        assertEquals(0,taskManager.getSubtasks().size(),"Ошибка удаления сабтасков");
    }
    //работает
    @Test
    public void deleteAllTest(){
        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.deleteAllTask();
        assertEquals(0,taskManager.getTasks().size(),"Ошибка удаления ");
        assertEquals(0,taskManager.getEpics().size(),"Ошибка удаления ");
        assertEquals(0,taskManager.getSubtasks().size(),"Ошибка удаления ");
    }
    //Работает
    @Test
    public void getPrioritizedTasksTest(){
        assertNull(taskManager.getPrioritizedTasks(),"список не пуст");

        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(2,taskManager.getPrioritizedTasks().size(),"Выводится лишний элемент  ");

        Throwable exceptionTask = assertThrows(IllegalArgumentException.class,()->taskManager.addTask(task),"Нет ошибки есть пересечение");
        assertEquals(IllegalArgumentException.class,exceptionTask.getClass(),"Другая ошибка"+exceptionTask.getClass());
        Throwable exceptionEpic = assertThrows(IllegalArgumentException.class,()->taskManager.addTask(epic),"Нет ошибки есть пересечение");
        assertEquals(IllegalArgumentException.class,exceptionEpic.getClass(),"Другая ошибка"+exceptionEpic.getClass());
        Throwable exceptionSubtask = assertThrows(IllegalArgumentException.class,()->taskManager.addTask(subtask),"Нет ошибки есть пересечение");
        assertEquals(IllegalArgumentException.class,exceptionSubtask.getClass(),"Другая ошибка"+exceptionSubtask.getClass());

    }

    //Работает
    @Test
    public void getByIDTest(){
        assertNull(taskManager.getTaskById(-1),"Получает несуществующий task");
        assertNull(taskManager.getEpicById(-1),"Получает несуществующий epic");
        assertNull(taskManager.getSubtaskById(-1),"Получает несуществующий subtask");

        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(task,taskManager.getTaskById(task.getId()),"Не получает существующий task");
        assertEquals(subtask,taskManager.getSubtaskById(subtask.getId()),"Не получает существующий subtask");
        assertEquals(epic,taskManager.getEpicById(epic.getId()),"Не получает существующий epic");
        assertEquals(subtask,taskManager.gettingSubtaskFromEpic(epic.getId()).get(0) , "Не получает существующий subtask по id epic ");
        assertNull(taskManager.gettingSubtaskFromEpic(-1),"Получает по subtask по несуществующему id ");

    }



}
