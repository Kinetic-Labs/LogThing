package com.github.kinetic.logthing.utils.web;

import com.github.kinetic.logthing.utils.io.fs.Resource;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class WebUtils {
    public WebUtils() {
    }

    public void send404(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 404, "404 Not Found");
    }

    public void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public void send405(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 405, "405 Method Not Allowed");
    }

    private String getContentType(String fileName) {
        String extension = "";
        int lastDotIndex = fileName.lastIndexOf('.');

        if(lastDotIndex > 0)
            extension = fileName.substring(lastDotIndex);

        return switch(extension.toLowerCase()) {
            case ".html" -> "text/html";
            case ".css" -> "text/css";
            case ".js" -> "application/javascript";
            case ".png" -> "image/png";
            case ".jpg", ".jpeg" -> "image/jpeg";
            case ".gif" -> "image/gif";
            case ".svg" -> "image/svg+xml";
            case ".ico" -> "image/x-icon";
            case ".json" -> "application/json";
            case ".xml" -> "application/xml";
            case ".txt" -> "text/plain";
            default -> "application/octet-stream";
        };
    }

    public void serveResource(HttpExchange exchange, String resourcePath) throws IOException {
        Resource resource = new Resource("", resourcePath);
        byte[] content = resource.readBytes();

        if(content != null) {
            String contentType = getContentType(resourcePath);
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, content.length);

            try(OutputStream os = exchange.getResponseBody()) {
                os.write(content);
            }
        } else {
            send404(exchange);
        }
    }
}
