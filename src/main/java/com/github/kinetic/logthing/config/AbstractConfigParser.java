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
     * @param path the file to config to be parsed
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
     * Get file of config file to be parsed
     *
     * @return the file
     */
    public String getPath() {
        return path;
    }
}
