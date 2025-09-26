package com.github.kinetic.logthing.web;


import com.github.kinetic.logthing.utils.io.WebUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static com.github.kinetic.logthing.web.Constants.WEB_ROOT;

public class WebHandler implements HttpHandler {
    private final WebUtils webUtils;

    public WebHandler() {
        this.webUtils = new WebUtils();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String relativePath = path.substring(4);

        if(relativePath.isEmpty())
            relativePath = "/html/index.html";

        if(!relativePath.startsWith("/"))
            relativePath = "/" + relativePath;

        webUtils.serveResource(exchange, WEB_ROOT + relativePath);
    }
}
