package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.utils.web.WebConstants.WEB_ROOT;

public final class RootHandler extends AbstractHandler {

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();

        if(path.equals("/")) {
            webUtils.serveResource(exchange, WEB_ROOT + "/html/index.html");
        } else {
            webUtils.send404(exchange);
        }
    }
}
