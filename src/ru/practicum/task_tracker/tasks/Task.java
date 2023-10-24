package ru.practicum.task_tracker.tasks;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;
import ru.practicum.task_tracker.manager.InMemoryTaskManager;
import ru.practicum.task_tracker.manager.Manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class Task {
    protected long id;
    protected String name ;
    protected String desc;
    protected  TaskStatus status;
    protected TaskType type;
    protected int duration ;
    protected LocalDateTime startTime;
    private LocalDateTime endTime = LocalDateTime.now() ;
    private static long countId=0;

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public Task(String name, String desc,String startTime,int duration){
        this.name =name;
        this.desc = desc;
        this.status = TaskStatus.NEW;
        this.type = TaskType.TASK;
        this.id=generateId();
        this.startTime = LocalDateTime.parse(startTime,FORMATTER);
        this.duration = duration;

    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    private long generateId(){

        return ++countId;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                ", startTime=" + startTime +
                '}';
    }

    public TaskType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public TaskStatus getStatus() {

        return status;
    }

    public void setStatus(TaskStatus status) {

        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && duration == task.duration && name.equals(task.name) && desc.equals(task.desc) && status == task.status && type == task.type && startTime.equals(task.startTime);
    }


}


