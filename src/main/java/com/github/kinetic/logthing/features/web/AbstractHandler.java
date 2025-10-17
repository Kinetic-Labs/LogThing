package com.github.kinetic.logthing.features.web;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.impl.RequestReceivedEvent;
import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.misc.ConfigUtil;
import com.github.kinetic.logthing.util.web.MethodUtil;
import com.github.kinetic.logthing.util.web.WebUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Abstract class for handling network requests
 */
public abstract class AbstractHandler implements HttpHandler {

    /**
     * Utilities commonly used for working with web requests/responses
     */
    protected final WebUtil webUtil;
    protected final Gson gson;
    protected final LogUtil log;
    protected final MethodUtil methodUtil;
    protected final ConfigUtil configUtil;

    /**
     * The name of the {@link AbstractHandler}
     */
    private final String name;

    /**
     * Create an instance of {@link AbstractHandler}
     */
    protected AbstractHandler(final String name) {
        this.webUtil = new WebUtil();
        this.log = new LogUtil();
        this.gson = new Gson();
        this.methodUtil = new MethodUtil();
        this.configUtil = new ConfigUtil();

        this.name = name;
    }

    /**
     * Handle a network request
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException on error whilst handling request, method will throw {@link IOException}
     */
    @Override
    public final void handle(final HttpExchange exchange) throws IOException {
        Thread.currentThread().setName("WD");
        LogThing.getInstance().getEventBus().dispatch(new RequestReceivedEvent(exchange));
        handleRequest(exchange);
    }

    /**
     * Handle a network request
     *
     * @param exchange {@link HttpExchange}
     * @throws IOException on error whilst handling request, method will throw {@link IOException}
     */
    public abstract void handleRequest(final HttpExchange exchange) throws IOException;

    /**
     * @return the name of {@link AbstractHandler}
     */
    public final String getName() {
        return name;
    }
}
