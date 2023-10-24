package ru.practicum.task_tracker.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
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

    protected static Epic epic;
    protected static Subtask subtask;
    protected static Task task;
    protected TaskManager taskManager;

    @BeforeEach
    public void beforeEach(){
        taskManager = Manager.getDefault();
    }


    @BeforeAll
    public static void beforeAll(){
        epic =  new Epic("Test","TEst","22.01.2022 22:22",0);
        task = new Task("Test","TEst",  "22.02.2022 22:22",0);
        subtask = new Subtask("Test","TEst",  epic.getId(),"22.03.2022 22:22",0);
    }
    @Test
    public void addTest(){
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

    @Test
    public void calculationEndTime(){
        epic.addSubtaskId(subtask.getId());
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.calculationEndTime(task);
        taskManager.calculationEndTime(subtask);
        taskManager.calculationEndTime(epic);
        assertEquals(subtask.getEndTime().plus(subtask.getDuration(), ChronoUnit.MINUTES),epic.getEndTime(),"Ошибка с эпиком");
        assertEquals(subtask.getStartTime(),epic.getStartTime());
        assertEquals(task.getStartTime().plus(task.getDuration(),ChronoUnit.MINUTES),task.getEndTime(),"Ошибка с таском");
    }

    @Test
    public void updateTest(){
        Epic epic1 = new Epic("Test","TEst",  "22.02.2022 22:22",0);
        Subtask subtask1 = new Subtask("Test","TEst",  epic1.getId(),"22.02.2022 22:22",0);
        Task task1 = new Task("Test","TEst", "22.03.2022 22:22",0);
        taskManager.updateTask(task1);
        taskManager.updateEpic(epic1);
        taskManager.updateSubtask(subtask1);
        assertNotEquals(task,taskManager.getTasks().get(task1.getId()));
        assertNotEquals(subtask,taskManager.getSubtasks().get(subtask1.getId()));
        assertNotEquals(epic,taskManager.getEpics().get(epic1.getId()));

    }

    @Test
    public void updateStatusEpic(){
        assertEquals(epic.getStatus(), TaskStatus.NEW);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);
        assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus());
    }

    @Test
    public void deleteByIndexTest(){
        taskManager.addTask(task);
        taskManager.addNewEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.deleteByIndexTask(task.getId());
        taskManager.deleteByIndexEpic(epic.getId());
        taskManager.deleteByIndexSubtask(subtask.getId());
        assertEquals(0,taskManager.getTasks().size(),"Ошибка удаления тасков");
        assertEquals(0,taskManager.getEpics().size(),"Ошибка удаления эпиков");
        assertEquals(0,taskManager.getSubtasks().size(),"Ошибка удаления сабтасков");
    }
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



}
