package com.github.kinetic.logthing.features.web.impl.html;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.util.web.WebConstants.WEB_ROOT;

/**
 * Serves the dashboard page
 */
public final class DashboardHtmlHandler extends AbstractHandler {

    /**
     * Create a new {@link DashboardHtmlHandler}
     */
    public DashboardHtmlHandler() {
        super("DashboardHandler");
    }

    /**
     * Handle the request
     *
     * @param exchange {@link HttpExchange} the request is coming from
     * @throws IOException on error, throws {@link IOException}
     */
    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        webUtil.serveResource(exchange, WEB_ROOT + "/html/dashboard.html");
    }
}
