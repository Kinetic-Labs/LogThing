package com.github.kinetic.logthing.web;

import com.github.kinetic.logthing.utils.io.WebUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static com.github.kinetic.logthing.web.Constants.WEB_ROOT;

public class Root implements HttpHandler {
    private final WebUtils webUtils;

    public Root() {
        this.webUtils = new WebUtils();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if(path.equals("/")) {
            webUtils.serveResource(exchange, WEB_ROOT + "/html/index.html");
        } else {
            webUtils.send404(exchange);
        }
    }
}
