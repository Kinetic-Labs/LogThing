package com.github.kinetic.logthing.features.web.impl.html;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static com.github.kinetic.logthing.util.web.WebConstants.WEB_ROOT;

public final class SettingsHtmlHandler extends AbstractHandler {

    public SettingsHtmlHandler() {
        super("SettingsHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        webUtil.serveResource(exchange, WEB_ROOT + "/html/settings.html");
    }
}
