package com.github.kinetic.logthing.features.web.impl.api.settings;

import com.github.kinetic.logthing.features.web.AbstractHandler;
import com.github.kinetic.logthing.module.impl.data.LogConsumerModule;
import com.github.kinetic.logthing.module.impl.misc.RequestLoggerModule;
import com.github.kinetic.logthing.util.misc.ModuleUtil;
import com.github.kinetic.logthing.util.web.Method;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class SettingsApiSetHandler extends AbstractHandler {

    public SettingsApiSetHandler() {
        super("SettingsApiHandler");
    }

    @Override
    public void handleRequest(final HttpExchange exchange) throws IOException {
        methodUtil.requireMethod(exchange, Method.POST);

        final InputStream inputStream = exchange.getRequestBody();
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader reader = new BufferedReader(inputStreamReader);
        final String json = reader.readLine();
        final JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        final String key = jsonObject.keySet().iterator().next();
        final String value = jsonObject.get(key).getAsString();

        updateSetting(key, value);

        webUtil.sendResponse(exchange, 200);
    }

    void updateSetting(final String setting, final String value) {
        final ModuleUtil moduleUtil = new ModuleUtil();

        switch(setting) {
            case "memoryStorage":
                moduleUtil.setModuleState(value, LogConsumerModule.class);
                break;
            case "requestLogger":
                moduleUtil.setModuleState(value, RequestLoggerModule.class);
                break;
            default:
                log.error("Unknown setting: " + setting);
        }
    }
}
