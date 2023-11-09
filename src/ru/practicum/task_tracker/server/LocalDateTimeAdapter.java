package ru.practicum.task_tracker.server;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
        if(localDateTime!=null) {
            jsonWriter.value(localDateTime.format(FORMATTER));
        }
        else {
            jsonWriter.value("null");
        }
    }

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        if(jsonReader.nextString().equals("null")){
            return null;
        }
        return LocalDateTime.parse(jsonReader.nextString(), FORMATTER);
    }
}