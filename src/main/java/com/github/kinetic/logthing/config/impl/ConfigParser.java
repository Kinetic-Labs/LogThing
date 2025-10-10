package com.github.kinetic.logthing.config.impl;

import com.github.kinetic.logthing.config.AbstractConfigParser;
import com.github.kinetic.logthing.config.ConfigLoader;
import com.github.kinetic.logthing.config.dsl.Config;

public final class ConfigParser extends AbstractConfigParser {

    public ConfigParser() {
    }

    @Override
    public Config parse() {
        return ConfigLoader.INSTANCE.loadConfig("config.logthing.kts");
    }
}
