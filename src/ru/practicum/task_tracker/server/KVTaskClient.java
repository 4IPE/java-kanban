package ru.practicum.task_tracker.server;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class KVTaskClient {

    private final String apiToken;
    private final String uriString;

    public KVTaskClient(String uriString) throws Exception {
         this.uriString = uriString;
         URI uri = new URI(uriString+"/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler =HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request,handler) ;
        this.apiToken = response.body();
    }
    public void put(String key,String json) throws Exception{
        URI uri = new URI(uriString+"/save/"+key+"?API_TOKEN="+apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler =HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request,handler) ;
    }

    public String load(String key) throws Exception{
        URI uri = new URI(uriString+"/load/"+key+"?API_TOKEN="+apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request,handler);
        return response.body();
    }

}
