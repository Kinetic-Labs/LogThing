package com.github.kinetic.logthing.util.web;

import com.github.kinetic.logthing.util.Util;
import com.github.kinetic.logthing.util.io.fs.ResourceUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public final class WebUtil implements Util {

    /**
     * Sends a 404 response
     *
     * @param exchange the {@link HttpExchange}
     * @throws IOException on error, throws {@link IOException}
     */
    public void send404(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 404, "404 Not Found");
    }

    /**
     * Send a response
     *
     * @param exchange   the {@link HttpExchange}
     * @param statusCode the status to send
     * @param response   the response to send
     * @throws IOException on error, throws {@link IOException}
     */
    public void sendResponse(final HttpExchange exchange, final int statusCode, final String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());

        try(final OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    /**
     * Send a method not allowed (405) error
     *
     * @param exchange the {@link HttpExchange}
     * @throws IOException on error, throws {@link IOException}
     */
    public void send405(final HttpExchange exchange) throws IOException {
        sendResponse(exchange, 405, "405 Method Not Allowed");
    }

    /**
     * Get the content type of file
     *
     * @param fileName the name of file
     * @return the content type of file
     */
    private String getContentType(final String fileName) {
        final int lastDotIndex = fileName.lastIndexOf('.');

        String extension = "";

        // get the extension
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

    /**
     * Serve a resource from resources
     *
     * @param exchange     the {@link HttpExchange}
     * @param resourcePath path to resource
     * @throws IOException on error, throws {@link IOException}
     */
    public void serveResource(HttpExchange exchange, String resourcePath) throws IOException {
        final ResourceUtil resourceUtil = new ResourceUtil("", resourcePath);
        final byte[] content = resourceUtil.readBytes();
        final String contentType = getContentType(resourcePath);

        if(content == null) {
            send404(exchange);

            return;
        }

        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, content.length);

        try(final OutputStream os = exchange.getResponseBody()) {
            os.write(content);
        }
    }
}
