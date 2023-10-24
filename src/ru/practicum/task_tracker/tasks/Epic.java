package ru.practicum.task_tracker.tasks;

import ru.practicum.task_tracker.enumereits.TaskType;


import java.util.ArrayList;

public class Epic extends Task {
   protected ArrayList<Long> subtaskIds;
    public Epic(String name, String desc,String startTime,int duration) {
        super(name, desc,startTime,duration);
        subtaskIds = new ArrayList<>();
        this.type = TaskType.EPIC;
    }

    public ArrayList<Long> getSubtaskIds() {
        return subtaskIds;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    public void addSubtaskId(long id){
        subtaskIds.add(id);
    }


}
