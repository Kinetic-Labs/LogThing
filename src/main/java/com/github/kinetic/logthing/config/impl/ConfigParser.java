package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.AbstractConfigParser;
import com.github.kinetic.logthing.config.ConfigLoader;
import com.github.kinetic.logthing.config.dsl.Config;

/**
 * Implementation of {@link AbstractConfigParser} for parsing the config file
 */
public final class ConfigParser extends AbstractConfigParser {

    /**
     * Create a new {@link ConfigParser}
     */
    public ConfigParser() {
    }

    /**
     * Parse the config file
     *
     * @return the parsed config
     */
    @Override
    public Config parse() {
        return ConfigLoader.INSTANCE.loadConfig("config.logthing.kts");
    }
}
