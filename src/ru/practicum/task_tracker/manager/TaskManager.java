package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;
import java.util.ArrayList;
import java.util.List;
public interface TaskManager {


    List<Task> getHistory();


    long addNewEpic(Epic epic);
    Long addSubtask(Subtask subtask);
    Long addTask(Task task);


    void updateTask(Task task);
    void updateSubtask(Subtask subtask);
    void updateEpic(Epic epic);
    void updateStatusEpic(long epicId);


     Task gettingByIndexTask(long id);
     Epic gettingByIndexEpic(long id);
     Subtask gettingByIndexSubtask(long id);
     ArrayList<Subtask> gettingSubtaskFromEpic(long epicId);


     void deleteAllTask();

     void deleteByIndexTask(long id );
     void deleteByIndexEpic(long id );
     void deleteByIndexSubtask(long id );

     void printAllTask();


}
