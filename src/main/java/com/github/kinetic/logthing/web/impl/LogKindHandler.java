package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public final class LogKindHandler extends AbstractHandler {

    public LogKindHandler() {
        super("LogKindHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        final String json = gson.toJson(LogThing.getInstance().getLogThingConfig().inputKey().inputFileKey().inputsFileLogKinds());

        webUtil.sendResponse(exchange, 200, json);
    }
}
