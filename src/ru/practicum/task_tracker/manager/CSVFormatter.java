package ru.practicum.task_tracker.manager;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;
import ru.practicum.task_tracker.tasks.Epic;
import ru.practicum.task_tracker.tasks.Subtask;
import ru.practicum.task_tracker.tasks.Task;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CSVFormatter {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private CSVFormatter(){}
    public static String toString(Task task){
         String name =  task.getName();
         String id =String.valueOf(task.getId());
         String desc = task.getDesc();
         String status = String.valueOf(task.getStatus());
         String type = String.valueOf(TaskType.valueOf(task.getClass().getSimpleName().toUpperCase()));
         String time = task.getStartTime().format(FORMATTER);
         String duration = String.valueOf(task.getDuration());
        if(task instanceof Subtask) {
            long epicId = ((Subtask) task).getEpicId();
            return String.join(",",id,type,name,status,desc,time,duration,String.valueOf(epicId));
        }
         return String.join(",",id,type,name,status,desc,time,duration);
    }

    public static Task fromString(String taskStr){
        String[] tokens = taskStr.split(",");
        long id = Long.parseLong(tokens[0]);
        TaskType type = TaskType.valueOf(tokens[1]);
        String name =tokens[2];
        TaskStatus status = TaskStatus.valueOf(tokens[3]);
        String desc = tokens[4];
        String startTime = tokens[5];
        int duration =Integer.parseInt( tokens[6]);
        switch (type){
            case TASK:
                Task task = new Task(name,desc,startTime,duration);
                task.setId(id);
                task.setStatus(status);
                return task;
            case EPIC:
                Epic epic =  new Epic(name,desc,startTime,duration);
                epic.setId(id);
                epic.setStatus(status);
                return  epic;
            case SUBTASK:
                Subtask subtask = new Subtask(name,desc,Long.parseLong(tokens[7]),startTime,duration);
                subtask.setId(id);
                subtask.setStatus(status);
                return subtask;
        }
        return null;
    }

    public static String historyToString(HistoryManager manager){
        List<Task> historyCheck = manager.getHistory();
        StringJoiner strHis = new StringJoiner(",");
        for(Task task :historyCheck){
            strHis.add(String.valueOf(task.getId()));
        }
        return strHis.toString();

    }
    public static List<Integer> historyFromString(String historyStr){
        if(!historyStr.isBlank()){
            String[] historyMas = historyStr.split(",");
            List<Integer> historyArr = new ArrayList<>();
            for (String element : historyMas) {
                historyArr.add(Integer.parseInt(element));
            }
            return historyArr;
        }
        return null;
    }
}
