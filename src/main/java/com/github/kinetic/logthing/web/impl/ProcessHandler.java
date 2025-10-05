package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.utils.types.Log;
import com.github.kinetic.logthing.web.BaseHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public final class ProcessHandler extends BaseHandler {

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        if("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            final String body = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody()))
                    .lines()
                    .collect(Collectors.joining("\n"));
            final String response = "File uploaded successfully.";

            final Log log = new Log(body);

            LogThing.getInstance().getEventBus().dispatch(new ProcessLogEvent(log));

            webUtils.sendResponse(exchange, 200, response);
        } else {
            webUtils.send405(exchange);
        }
    }
}