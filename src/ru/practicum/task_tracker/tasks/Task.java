package ru.practicum.task_tracker.tasks;

import ru.practicum.task_tracker.enumereits.TaskStatus;

public class Task {
    protected long id;
    protected String name ;
    protected String desc;
    protected  String status;
    private static long count=0;

    public Task(String name, String desc){
        this.name =name;
        this.desc = desc;
        this.status = String.valueOf(TaskStatus.NEW);
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

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

}
