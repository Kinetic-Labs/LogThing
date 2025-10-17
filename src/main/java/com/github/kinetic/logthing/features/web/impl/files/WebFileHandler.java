package com.github.kinetic.logthing.features.web.impl.files;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.util.web.WebConstants.WEB_ROOT;

/**
 * Serves static files from the web root
 */
public final class WebFileHandler extends AbstractHandler {

    /**
     * Create a new {@link WebFileHandler}
     */
    public WebFileHandler() {
        super("WebHandler");
    }

    /**
     * Handle the request
     *
     * @param exchange {@link HttpExchange} the request is coming from
     * @throws IOException on error, throws {@link IOException}
     */
    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        final String path = exchange.getRequestURI().getPath();
        final String relativePath = path.substring(4);

        webUtil.serveResource(exchange, WEB_ROOT + relativePath);
    }
}
