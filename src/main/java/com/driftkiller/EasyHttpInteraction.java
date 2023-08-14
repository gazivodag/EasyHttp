package com.driftkiller;

import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import lombok.AllArgsConstructor;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@SuppressWarnings("unused")
public class EasyHttpInteraction {

    private HttpExchange exchange;

    public Map<String, Object> getBodyJson() throws IOException {
        // Get the input stream from HttpExchange.getBody()
        InputStream inputStream = exchange.getRequestBody();

        // Convert the input stream to a string
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String jsonString = stringBuilder.toString();

        // Create Type for Gson to parse the JSON into a Map<String, Object>
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();

        // Parse the JSON string into a Map<String, String> using Gson
        Map<String, Object> jsonMap = EasyHttpServer.GSON.fromJson(jsonString, type);
        return jsonMap == null ? new HashMap<>() : jsonMap;
    }

    public void setContentType(ContentType contentType) {
        exchange.getResponseHeaders().set("Content-Type", contentType.getValue());
    }

    public void json(Object serializableObject) throws IOException {
        json(serializableObject, 200);
    }

    public void json(Object serializableObject, int code) throws IOException {
        setContentType(ContentType.APPLICATION_JSON);
        Class<?> clazz = serializableObject.getClass();
        String asJson = EasyHttpServer.GSON.toJson(serializableObject, clazz);
        send(asJson, code);
    }

    public void send(String message) throws IOException {
        send(message, 200);
    }

    public void send(String message, int code) throws IOException {
        exchange.sendResponseHeaders(code, message.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    public void sendAttachment(byte[] bytes, ContentType contentType, String filename) throws IOException {
        setContentType(contentType);
        exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=" + filename);
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
        exchange.close();
    }


}
