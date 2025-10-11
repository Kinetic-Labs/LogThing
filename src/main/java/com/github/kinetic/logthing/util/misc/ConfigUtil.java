package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.dsl.*;
import com.github.kinetic.logthing.util.Util;

public class ConfigUtil implements Util {

    private final LogThing logThing = LogThing.getInstance();

    public WebConfig getWebConfig() {
        return logThing.getLogThingConfig().getWeb();
    }

    public InputsConfig getInputsConfig() {
        return logThing.getLogThingConfig().getInputs();
    }

    public AlertsConfig getAlertsConfig() {
        return logThing.getLogThingConfig().getAlerts();
    }
}
