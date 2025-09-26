package com.github.kinetic.logthing.utils.io;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WebUtils {
    private final LogUtils log;

    public WebUtils() {
        log = new LogUtils();
    }

    public void send404(HttpExchange exchange) throws IOException {
        String response = "404 Not Found";
        exchange.sendResponseHeaders(404, response.length());

        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
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
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if(inputStream != null) {
                byte[] content = inputStream.readAllBytes();

                String contentType = getContentType(resourcePath);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, content.length);

                try(OutputStream os = exchange.getResponseBody()) {
                    os.write(content);
                }
            } else {
                log.error("Resource not found: " + resourcePath);
                send404(exchange);
            }
        }
    }
}
