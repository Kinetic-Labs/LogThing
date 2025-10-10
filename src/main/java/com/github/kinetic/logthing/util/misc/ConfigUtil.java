package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.dsl.*;
import com.github.kinetic.logthing.util.Util;

public class ConfigUtil implements Util {

    private final LogThing logThing = LogThing.getInstance();
    private final Config config = logThing.getLogThingConfig();

    public WebConfig getWebConfig() {
        return config.getWeb();
    }

    public InputsConfig getInputsConfig() {
        return config.getInputs();
    }

    public AlertsConfig getAlertsConfig() {
        return config.getAlerts();
    }
}
