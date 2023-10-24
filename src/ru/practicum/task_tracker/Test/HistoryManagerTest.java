package ru.practicum.task_tracker.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.practicum.task_tracker.CustomLinkedList;
import ru.practicum.task_tracker.manager.HistoryManager;
import ru.practicum.task_tracker.manager.Manager;
import ru.practicum.task_tracker.manager.TaskManager;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private static HistoryManager historyManager;
    private static Epic epic;
    private static Subtask subtask;
    private static Task task;


    @BeforeAll
    public static void beforeAll(){
        epic =  new Epic("Test","TEst","22.01.2023 22:22",0);
        subtask =  new Subtask("Test","TEst", epic.getId(), "22.02.2023 22:22",0);
        task = new Task("Test","TEst",  "22.03.2023 22:22",0);
    }
    @BeforeEach
    public void beforeEach(){
        historyManager = Manager.getDefaultHistoryManager();
    }
    @Test
    public void testAddTaskInHistory() {
        assertTrue(historyManager.getHistory().isEmpty(),"История не пустая");
        historyManager.addTask(task);
        historyManager.addTask(subtask);
        historyManager.addTask(epic);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История пустая.");
        assertEquals(3, history.size(), "История не пустая.");
        assertEquals(task, history.get(2), "Очередность истории неправильная.");
        assertEquals(subtask, history.get(1), "Очередность истории неправильная.");
        assertEquals(epic, history.get(0), "Очередность истории неправильная.");

        // дублирование
        historyManager.addTask(task);
        history = historyManager.getHistory();

        assertEquals(3, history.size(), "История не пустая.");
        assertEquals(subtask, history.get(2), "Очередность истории неправильная.");
        assertEquals(epic, history.get(1), "Очередность истории неправильная.");
        assertEquals(task, history.get(0), "Очередность истории неправильная.");
    }

    @Test
    public void testRemoveByIndexHistory(){
        historyManager.addTask(task);
        historyManager.addTask(subtask);
        historyManager.addTask(epic);
        historyManager.remove(1);
        assertEquals(2,historyManager.getHistory().size());
        historyManager.remove(2);
        assertEquals(1,historyManager.getHistory().size());
        historyManager.remove(3);
        assertTrue(historyManager.getHistory().isEmpty());

        //Дублирование
        historyManager.remove(1);
        assertTrue(historyManager.getHistory().isEmpty());
        historyManager.remove(2);
        assertTrue(historyManager.getHistory().isEmpty());
        historyManager.remove(3);
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    public void getPrioritizedTasksTest(){
        assertEquals(0,historyManager.getPrioritizedTasks().size());
        historyManager.addTask(task);
        historyManager.addTask(subtask);
        historyManager.addTask(epic);
        List<Task> sort1  = new ArrayList<>(historyManager.getPrioritizedTasks());
        assertTrue(sort1.get(0).equals(task) && sort1.get(1).equals(subtask) && sort1.get(2).equals(epic));

        //Дублирование
        historyManager.addTask(task);
        historyManager.addTask(subtask);
        historyManager.addTask(epic);
        List<Task> sort2  = new ArrayList<>(historyManager.getPrioritizedTasks());
        assertTrue(sort2.get(0).equals(task) && sort2.get(1).equals(subtask) && sort2.get(2).equals(epic));
    }




}