package com.github.kinetic.logthing.config;

import com.github.kinetic.logthing.config.type.LogThingConfig;

/**
 * Abstract class to create a config parser
 */
public abstract class AbstractConfigParser implements Config {

    private final String path;

    /**
     * Create a new {@link AbstractConfigParser}
     *
     * @param path the path to config to be parsed
     */
    public AbstractConfigParser(final String path) {
        this.path = path;
    }

    /**
     * Parse the config
     *
     * <p>
     * When overridden, the class will implement logic to parse the config file
     * </p>
     *
     * @return parsed config options for {@link LogThingConfig}
     */
    protected abstract LogThingConfig parse();

    /**
     * Get path of config file to be parsed
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }
}
