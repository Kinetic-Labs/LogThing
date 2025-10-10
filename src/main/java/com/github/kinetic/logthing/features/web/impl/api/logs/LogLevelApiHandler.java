package com.github.kinetic.logthing.features.web.impl.api.logs;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.type.LogThingConfig;
import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.util.web.Method;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public final class LogLevelApiHandler extends AbstractHandler {

    public LogLevelApiHandler() {
        super("LogLevelApiHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.GET);

        final LogThingConfig config = LogThing.getInstance().getLogThingConfig();
        final List<Object> logKinds = config.inputKey().inputFileKey().inputsFileLogKinds();
        final String json = gson.toJson(logKinds);

        webUtil.sendResponse(exchange, 200, json);
    }
}
