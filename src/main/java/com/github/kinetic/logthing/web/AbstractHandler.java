package com.github.kinetic.logthing.web;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.impl.RequestReceivedEvent;
import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.web.WebUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class AbstractHandler implements HttpHandler {
    protected final WebUtils webUtils;
    protected final LogUtils log;

    public AbstractHandler() {
        this.webUtils = new WebUtils();
        this.log = new LogUtils();
    }

    @Override
    public final void handle(final HttpExchange exchange) throws IOException {
        Thread.currentThread().setName("WD");
        LogThing.getInstance().getEventBus().dispatch(new RequestReceivedEvent(exchange));
        handleRequest(exchange);
    }

    /**
     * Handle a network request
     * @param exchange {@link HttpExchange}
     * @throws IOException on error whilst handling request, method will throw {@link IOException}
     */
    public abstract void handleRequest(final HttpExchange exchange) throws IOException;
}
