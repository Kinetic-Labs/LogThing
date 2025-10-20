package com.github.kinetic.logthing.config;

import com.github.kinetic.logthing.config.dsl.Config;

/**
 * Abstract class to create a config parser
 */
public abstract class AbstractConfigParser implements IConfig {

    /**
     * Create a new {@link AbstractConfigParser}
     */
    public AbstractConfigParser() {}

    /**
     * Parse the config
     *
     * <p>
     * When overridden, the class will implement logic to parse the config file
     * </p>
     *
     * @return parsed config options for {@link Config}
     */
    protected abstract Config parse();
}
