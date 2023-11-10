package ru.practicum.task_tracker.server.adapter;

import com.google.gson.*;
import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;
import ru.practicum.task_tracker.tasks.Subtask;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SubtaskDeserialize implements JsonDeserializer<Subtask> {
    @Override
    public Subtask deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
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
        long epicId = jsonObject.get("epicID").getAsLong();
        Subtask subtask = new Subtask(name,desc,epicId,startTime,duration);
        subtask.setId(jsonObject.get("id").getAsLong());
        subtask.setStatus(TaskStatus.valueOf(jsonObject.get("status").getAsString()));
        subtask.setType(TaskType.valueOf(jsonObject.get("type").getAsString()));

        if(subtask.getStartTime()==null) {
            subtask.setEndTime(null);
        }
        else{
            subtask.setEndTime(LocalDateTime.parse(jsonObject.get("endTime").getAsString(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        }

        return subtask;

    }
}
