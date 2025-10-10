package com.github.kinetic.logthing.features.web.impl.api.logs;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.util.web.Method;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class LogLevelApiHandler extends AbstractHandler {

    public LogLevelApiHandler() {
        super("LogLevelApiHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.GET);

        final List<String> logKinds = Objects.requireNonNull(
                configUtil.getInputsConfig().getFile()
        ).getLogKinds();

        final String json = gson.toJson(logKinds);

        webUtil.sendResponse(exchange, 200, json);
    }
}
