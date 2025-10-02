package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.impl.PreProcessLog;
import com.github.kinetic.logthing.utils.types.Log;
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

            Log log = new Log(body);

            LogThing.getEventBus().dispatch(new PreProcessLog(log));

            webUtils.sendResponse(exchange, 200, response);
        } else {
            webUtils.send405(exchange);
        }
    }
}