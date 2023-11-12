package ru.practicum.task_tracker.Test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_tracker.manager.HistoryManager;
import ru.practicum.task_tracker.manager.Manager;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private  HistoryManager historyManager;
    private  Epic epic;
    private  Subtask subtask;
    private  Task task;



    @BeforeEach
    public void beforeEach(){
        historyManager = Manager.getDefaultHistoryManager();
        epic =  new Epic("Test","TEst","22.01.2023 22:22",10);
        subtask =  new Subtask("Test","TEst", epic.getId(), "22.02.2023 22:22",10);
        task = new Task("Test","TEst",  "22.03.2023 22:22",10);

    }
    @Test
    public void testAddTaskInHistory() {
        historyManager.addTask(null);
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
        assertEquals(3,historyManager.getHistory().size());
        historyManager.remove(task.getId());
        assertEquals(2,historyManager.getHistory().size());
        historyManager.remove(subtask.getId());
        assertEquals(1,historyManager.getHistory().size());
        historyManager.remove(epic.getId());
        assertTrue(historyManager.getHistory().isEmpty());


        historyManager.remove(task.getId());
        assertTrue(historyManager.getHistory().isEmpty());
        historyManager.remove(subtask.getId());
        assertTrue(historyManager.getHistory().isEmpty());
        historyManager.remove(epic.getId());
        assertTrue(historyManager.getHistory().isEmpty());
    }






}