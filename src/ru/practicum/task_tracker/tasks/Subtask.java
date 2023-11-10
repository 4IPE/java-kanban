package ru.practicum.task_tracker.tasks;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;

import java.time.LocalDateTime;

public class Subtask extends Task {
   private Long epicId;
    public Subtask(String name, String desc,Long epicId,String startTime,int duration) {
        super(name, desc,startTime,duration);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    public long getEpicId() {
        return epicId;
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
