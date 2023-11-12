package ru.practicum.task_tracker.server.adapter;

import com.google.gson.*;
import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;

import ru.practicum.task_tracker.tasks.Task;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskDeserialize implements JsonDeserializer<Task> {
    @Override
    public Task deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name =jsonObject.get("name").getAsString();
        String desc = jsonObject.get("desc").getAsString();
        String startTime;
        if(jsonObject.get("startTime")==null){
             startTime = null;
        }
        else{
             startTime = jsonObject.get("startTime").getAsString();
        }
        int duration = jsonObject.get("duration").getAsInt();
        Task task = new Task(name,desc,startTime,duration);
        task.setId(jsonObject.get("id").getAsLong());
        task.setStatus(TaskStatus.valueOf(jsonObject.get("status").getAsString()));
        task.setType(TaskType.valueOf(jsonObject.get("type").getAsString()));

        if(task.getStartTime()==null) {
            task.setEndTime(null);
        }
        else {
            task.setEndTime(LocalDateTime.parse(jsonObject.get("endTime").getAsString(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        }

        return task;


    }
}
