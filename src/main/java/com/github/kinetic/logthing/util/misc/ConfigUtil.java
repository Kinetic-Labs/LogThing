package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.dsl.AlertsConfig;
import com.github.kinetic.logthing.config.dsl.InputsConfig;
import com.github.kinetic.logthing.config.dsl.WebConfig;
import com.github.kinetic.logthing.util.Util;

/**
 * Utilities for working with the config
 */
public final class ConfigUtil implements Util {

    /**
     * LogThing instance
     */
    private final LogThing logThing = LogThing.getInstance();

    /**
     * Get the web config
     * @return the web config
     */
    public WebConfig getWebConfig() {
        return logThing.getLogThingConfig().getWeb();
    }

    /**
     * Get the inputs config
     * @return the inputs config
     */
    public InputsConfig getInputsConfig() {
        return logThing.getLogThingConfig().getInputs();
    }

    /**
     * Get the alerts config
     * @return the alerts config
     */
    public AlertsConfig getAlertsConfig() {
        return logThing.getLogThingConfig().getAlerts();
    }
}
