package com.github.kinetic.logthing.features.web.impl.api.settings;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.impl.MiscSettings;
import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.util.web.Method;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public final class SettingsApiGetHandler extends AbstractHandler {

    public SettingsApiGetHandler() {
        super("SettingsApiGetHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.GET);

        final MiscSettings moduleSettings = LogThing.getInstance().getModuleSettings();
        final Map<String, Map<String, Object>> moduleSettingsAllSettings = moduleSettings.getAllSettings();
        final String json = gson.toJson(moduleSettingsAllSettings);

        webUtil.sendResponse(exchange, 200, json);
    }
}
