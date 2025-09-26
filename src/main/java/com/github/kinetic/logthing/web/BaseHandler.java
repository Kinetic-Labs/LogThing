package com.github.kinetic.logthing.web;

import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.web.WebUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;

public abstract class BaseHandler implements HttpHandler {
    protected final WebUtils webUtils;
    protected final LogUtils log;

    public BaseHandler() {
        this.webUtils = new WebUtils();
        this.log = new LogUtils();
    }

    @Override
    public final void handle(HttpExchange exchange) throws IOException {
        Thread.currentThread().setName("WD");
        logRequest(exchange);
        handleRequest(exchange);
    }

    public abstract void handleRequest(HttpExchange exchange) throws IOException;

    public void logRequest(HttpExchange exchange) {
        String message = String.format(
                "[%s] Request received from %s: %s",
                exchange.getRequestMethod(),
                exchange.getRemoteAddress(),
                exchange.getRequestURI()
        );

        log.info(message);
    }
}
