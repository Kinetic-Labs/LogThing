package com.github.kinetic.logthing.features.web.impl.api;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.impl.ProcessLogEvent;
import com.github.kinetic.logthing.util.types.Log;
import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.util.web.Method;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class ProcessApiHandler extends AbstractHandler {

    public ProcessApiHandler() {
        super("ProcessHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.POST);

        final InputStream inputStream = exchange.getRequestBody();
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader reader = new BufferedReader(inputStreamReader);

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
