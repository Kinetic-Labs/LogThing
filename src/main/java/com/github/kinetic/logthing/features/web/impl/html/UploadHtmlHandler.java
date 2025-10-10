package com.github.kinetic.logthing.features.web.impl.html;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.util.web.WebConstants.WEB_ROOT;

public final class UploadHtmlHandler extends AbstractHandler {

    public UploadHtmlHandler() {
        super("RootHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        webUtil.serveResource(exchange, WEB_ROOT + "/html/upload.html");
    }
}
