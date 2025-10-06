package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.storage.LogStorage;
import com.github.kinetic.logthing.utils.types.ParsedLog;
import com.github.kinetic.logthing.web.AbstractHandler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class LogsHandler extends AbstractHandler {
    private final Gson gson = new Gson();

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        if("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            List<ParsedLog> logs = LogStorage.getInstance().getLogs();
            String json = gson.toJson(logs);
            webUtils.sendResponse(exchange, 200, json);
        } else {
            webUtils.send405(exchange);
        }
    }
}