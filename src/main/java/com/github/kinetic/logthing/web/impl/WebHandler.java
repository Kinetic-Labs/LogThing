package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.utils.web.WebConstants.WEB_ROOT;

public final class WebHandler extends AbstractHandler {

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        String relativePath = path.substring(4);

        if(!relativePath.startsWith("/"))
            relativePath = "/html/index.html";

        webUtils.serveResource(exchange, WEB_ROOT + relativePath);
    }
}
