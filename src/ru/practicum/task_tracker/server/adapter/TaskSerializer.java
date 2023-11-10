package ru.practicum.task_tracker.server.adapter;

import com.google.gson.*;
import ru.practicum.task_tracker.tasks.Task;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class TaskSerializer implements JsonSerializer<Task> {
    @Override
    public JsonElement serialize(Task task, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name",task.getName());
        result.addProperty("desc",task.getDesc());
        result.addProperty("id",task.getId());
        result.addProperty("type", String.valueOf(task.getType()));
        result.addProperty("status", String.valueOf(task.getStatus()));
        if(task.getStartTime()==null){
            result.addProperty("startTime", (String) null);
            result.addProperty("endTime",(String) null);
        }
        else{
            result.addProperty("startTime", task.getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            result.addProperty("endTime", task.getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        }

        result.addProperty("duration",task.getDuration());
        return result;
    }
}
