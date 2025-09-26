package com.github.kinetic.logthing.web.impl;

import com.github.kinetic.logthing.web.BaseHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.utils.web.Constants.WEB_ROOT;

public class RootHandler extends BaseHandler {

    @Override
    public void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.equals("/")) {
            webUtils.serveResource(exchange, WEB_ROOT + "/html/index.html");
        } else {
            webUtils.send404(exchange);
        }
    }
}
