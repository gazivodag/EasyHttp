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

    /**
     * Retrieves and parses the JSON body from an HttpExchange object.
     *
     * @return A Map representing the parsed JSON data, with String keys and Object values.
     * @throws IOException if there was an error reading the request body
     */
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

    /**
     * Sets the content type of the response.
     *
     * @param contentType the ContentType to be set
     */
    public void setContentType(ContentType contentType) {
        exchange.getResponseHeaders().set("Content-Type", contentType.getValue());
    }

    /**
     * Retrieve the first Content-Type of the request headers.
     *
     * @return The ContentType object representing the first Content-Type.
     * @throws NullPointerException     if the request headers or Content-Type header is null.
     * @throws IllegalArgumentException if the Content-Type header is not properly formatted.
     */
    public ContentType getFirstContentType() //this may potentially introduce side effects
    {
        String firstBlock = exchange.getRequestHeaders().getFirst("Content-Type");
        return ContentType.fromMime(firstBlock.split(";")[0]);
    }

    /**
     * Sets the content type to application/json and serializes the given object as JSON.
     * Sends the serialized json object as the response message with a default code of 200.
     *
     * @param serializableObject the object to be serialized and sent as the response message
     * @throws IOException if an I/O error occurs while sending the response
     */
    public void json(Object serializableObject) throws IOException {
        json(serializableObject, 200);
    }

    /**
     * Sets the content type to application/json and serializes the given object as JSON.
     * Sends the serialized json object as the response message with the specified code.
     *
     * @param serializableObject the object to be serialized and sent as the response message
     * @param code               the HTTP status code to be set in the response
     * @throws IOException if an I/O error occurs while sending the response
     */
    public void json(Object serializableObject, int code) throws IOException {
        setContentType(ContentType.APPLICATION_JSON);
        Class<?> clazz = serializableObject.getClass();
        String asJson = EasyHttpServer.GSON.toJson(serializableObject, clazz);
        send(asJson, code);
    }

    /**
     * Sends the specified message as the response with a default code of 200.
     *
     * @param message the message to be sent as the response
     * @throws IOException if an I/O error occurs while sending the response
     */
    public void send(String message) throws IOException {
        send(message, 200);
    }

    /**
     * Sends the specified message as the response with the specified code.
     *
     * @param message the message to be sent as the response
     * @param code    the HTTP status code to be set in the response
     * @throws IOException if an I/O error occurs while sending the response
     */
    public void send(String message, int code) throws IOException {
        exchange.sendResponseHeaders(code, message.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    /**
     * Sends an attachment as a response.
     *
     * @param bytes       the byte array representing the attachment
     * @param contentType the MIME type of the attachment
     * @param filename    the name of the attachment file
     * @throws IOException if an I/O error occurs during sending the attachment
     */
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
