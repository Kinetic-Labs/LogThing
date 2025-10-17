package com.github.kinetic.logthing.features.web.impl.api.logs;

import com.github.kinetic.logthing.features.storage.impl.memory.LogStorage;
import com.github.kinetic.logthing.util.types.ParsedLog;
import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.util.web.Method;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Set;

/**
 * Returns a list of all logs in the log storage
 */
public class LogsApiGetHandler extends AbstractHandler {

    /**
     * Create a new {@link LogsApiGetHandler}
     */
    public LogsApiGetHandler() {
        super("LogsApiGetHandler");
    }

    /**
     * Handle the request
     *
     * @param exchange {@link HttpExchange} the request is coming from
     * @throws IOException on error, throws {@link IOException}
     */
    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.GET);

        final Set<ParsedLog> logs = LogStorage.getInstance().getLogs();
        final String json = gson.toJson(logs);

        webUtil.sendResponse(exchange, 200, json);
    }
}