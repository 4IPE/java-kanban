package ru.practicum.task_tracker.tasks;

import ru.practicum.task_tracker.enumereits.TaskType;

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
                ", status='" + status + '\'' +
                '}';
    }

    public long getEpicId() {
        return epicId;
    }

}
