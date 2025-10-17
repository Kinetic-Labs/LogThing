package com.github.kinetic.logthing.features.web.impl.html;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.util.web.WebConstants.WEB_ROOT;

/**
 * Serves the settings page
 */
public final class SettingsHtmlHandler extends AbstractHandler {

    /**
     * Create a new {@link SettingsHtmlHandler}
     */
    public SettingsHtmlHandler() {
        super("SettingsHandler");
    }

    /**
     * Handle the request
     *
     * @param exchange {@link HttpExchange} the request is coming from
     * @throws IOException on error, throws {@link IOException}
     */
    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        webUtil.serveResource(exchange, WEB_ROOT + "/html/settings.html");
    }
}
