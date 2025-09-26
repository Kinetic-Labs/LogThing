package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.web.BaseHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ProcessHandler extends BaseHandler {

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        if("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            String body = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody()))
                    .lines()
                    .collect(Collectors.joining("\n"));
            String response = "File uploaded successfully.";

            log.debug(body);

            webUtils.sendResponse(exchange, 200, response);
        } else {
            webUtils.send405(exchange);
        }
    }
}