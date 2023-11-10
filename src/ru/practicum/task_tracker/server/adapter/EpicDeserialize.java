package ru.practicum.task_tracker.server.adapter;

import com.google.gson.*;
import ru.practicum.task_tracker.enumereits.TaskStatus;
import ru.practicum.task_tracker.enumereits.TaskType;
import ru.practicum.task_tracker.tasks.Epic;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EpicDeserialize implements JsonDeserializer<Epic> {
    @Override
    public Epic deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
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
        Epic epic = new Epic(name,desc,startTime,duration);
        epic.setId(jsonObject.get("id").getAsLong());
        epic.setStatus(TaskStatus.valueOf(jsonObject.get("status").getAsString()));
        epic.setType(TaskType.valueOf(jsonObject.get("type").getAsString()));
        JsonArray jsonArraySubtaskId = jsonObject.getAsJsonArray("subtaskIds");

        for(JsonElement id:jsonArraySubtaskId) {
            epic.addSubtaskId(id.getAsLong());
        }

        if(epic.getStartTime()==null) {
            epic.setEndTime(null);
        }
        else{
            epic.setEndTime(LocalDateTime.parse(jsonObject.get("endTime").getAsString(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        }

        return epic;

    }
}
