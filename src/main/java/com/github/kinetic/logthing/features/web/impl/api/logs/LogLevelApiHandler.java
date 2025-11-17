package com.github.kinetic.logthing.features.web.impl.api.logs;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.util.web.Method;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Returns a list of log kinds that can be used in the log file selector
 */
public final class LogLevelApiHandler extends AbstractHandler {

    /**
     * Create a new {@link LogLevelApiHandler}
     */
    public LogLevelApiHandler() {
        super("LogLevelApiHandler");
    }

    /**
     * Handle the request
     *
     * @param exchange {@link HttpExchange} the request is coming from
     * @throws IOException on error, throws {@link IOException}
     */
    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.GET);

        final List<String> logKinds = Objects.requireNonNull(
                configUtil.getInputsConfig().file()
        ).logKinds();

        final String json = gson.toJson(logKinds);

        webUtil.sendResponse(exchange, 200, json);
    }
}
