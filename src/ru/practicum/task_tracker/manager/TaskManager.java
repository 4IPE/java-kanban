package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface TaskManager {


     List<Task> getHistory();
    Set<Task> getPrioritizedTasks();

    Long addNewEpic(Epic epic);

     HashMap<Long, Epic> getEpics();

     HashMap<Long, Subtask> getSubtasks();

   HashMap<Long, Task> getTasks();
    void calculationEndTime(Task task);

    Long addSubtask(Subtask subtask);
    Long addTask(Task task);


    void updateTask(Task task);
    void updateSubtask(Subtask subtask);
    void updateEpic(Epic epic);


     Task getTaskById(long id);
     Epic getEpicById(long id);
     Subtask getSubtaskById(long id);
     ArrayList<Subtask> gettingSubtaskFromEpic(long epicId);


     void deleteAllTask();

     void deleteByIndexTask(long id );
     void deleteByIndexEpic(long id );
     void deleteByIndexSubtask(long id );

     void printAllTask();


}
