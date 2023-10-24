package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.CustomLinkedList;
import ru.practicum.task_tracker.tasks.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HistoryManager {
    void addTask(Task task);
    void remove(long id);

    List<Task> getHistory();

    Set<Task> getPrioritizedTasks();
}
