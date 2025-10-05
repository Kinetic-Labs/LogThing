package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.web.BaseHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.utils.web.Constants.WEB_ROOT;

public final class RootHandler extends BaseHandler {

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
