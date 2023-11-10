package ru.practicum.task_tracker.tasks;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;


import java.time.LocalDateTime;
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

    public void removeSubtaskId(long id){
        subtaskIds.remove(id);
        if(subtaskIds.isEmpty()){
            this.startTime = null;
        }
    }

    @Override
    public void setDuration(int duration) {
        super.setDuration(duration);
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        super.setEndTime(endTime);
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
    }

    @Override
    public void setType(TaskType type) {
        super.setType(type);
    }

    @Override
    public int getDuration() {
        return super.getDuration();
    }

    @Override
    public LocalDateTime getEndTime() {
        return super.getEndTime();
    }

    @Override
    public LocalDateTime getStartTime() {
        return super.getStartTime();
    }

    @Override
    public TaskType getType() {
        return super.getType();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public String getDesc() {
        return super.getDesc();
    }

    @Override
    public void setDesc(String desc) {
        super.setDesc(desc);
    }

    @Override
    public TaskStatus getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
    }
}
