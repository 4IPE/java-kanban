package ru.practicum.task_tracker.tasks;

import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;


public class Task {
    protected long id;
    protected String name ;
    protected String desc;
    protected  TaskStatus status;
    protected TaskType type;
    private static long count=0;

    public Task(String name, String desc){
        this.name =name;
        this.desc = desc;
        this.status = TaskStatus.NEW;
        this.type = TaskType.TASK;
        this.id=generateId();
    }
    private long generateId(){

        return ++count;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
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

}
