package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.storage.impl.memory.LogStorage;
import com.github.kinetic.logthing.util.types.ParsedLog;
import com.github.kinetic.logthing.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Set;

public class LogsHandler extends AbstractHandler {
    public LogsHandler() {
        super("LogsHandler");
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        if(!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            webUtil.send405(exchange);
            return;
        }

        final Set<ParsedLog> logs = LogStorage.getInstance().getLogs();
        final String json = gson.toJson(logs);

        webUtil.sendResponse(exchange, 200, json);
    }
}