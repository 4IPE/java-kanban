package ru.practicum.task_tracker.tasks;

import java.util.ArrayList;

public class Epic extends Task {
   protected ArrayList<Long> subtaskIds;
    public Epic(String name, String desc) {
        super(name, desc, "NEW");
        subtaskIds = new ArrayList<>();
    }

    public ArrayList<Long> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void addSubtaskId(long id){
        subtaskIds.add(id);
    }
}
