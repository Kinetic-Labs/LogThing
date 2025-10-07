package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.util.web.WebConstants.WEB_ROOT;

public final class WebHandler extends AbstractHandler {

    public WebHandler() {
        super("WebHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        final String relativePath = path.substring(4);

        webUtil.serveResource(exchange, WEB_ROOT + relativePath);
    }
}
