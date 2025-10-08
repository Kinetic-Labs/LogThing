package com.github.kinetic.logthing.features.web.impl;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.util.types.Log;
import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ProcessHandler extends AbstractHandler {

    public ProcessHandler() {
        super("ProcessHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        if(!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            webUtil.send405(exchange);

            return;
        }

        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody()));

        String line;
        while((line = reader.readLine()) != null) {
            if(!line.trim().isEmpty()) {
                final Log log = new Log(line);

                LogThing.getInstance().getEventBus().dispatch(new ProcessLogEvent(log));
            }
        }

        final String response = "File uploaded successfully.";

        webUtil.sendResponse(exchange, 200, response);
    }
}
