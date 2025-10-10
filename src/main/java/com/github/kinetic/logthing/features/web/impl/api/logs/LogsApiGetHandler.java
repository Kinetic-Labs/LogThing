package com.github.kinetic.logthing.features.web.impl.api.logs;

import com.github.kinetic.logthing.features.storage.impl.memory.LogStorage;
import com.github.kinetic.logthing.util.types.ParsedLog;
import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.util.web.Method;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Set;

public class LogsApiGetHandler extends AbstractHandler {
    public LogsApiGetHandler() {
        super("LogsApiGetHandler");
    }

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.GET);

        final Set<ParsedLog> logs = LogStorage.getInstance().getLogs();
        final String json = gson.toJson(logs);

        webUtil.sendResponse(exchange, 200, json);
    }
}