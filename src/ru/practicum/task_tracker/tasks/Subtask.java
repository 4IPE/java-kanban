package ru.practicum.task_tracker.tasks;

public class Subtask extends Task {
   private Long epicId;
    public Subtask(String name, String desc, String status,Long epicId) {
        super(name, desc, status);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public long getEpicId() {
        return epicId;
    }

}
