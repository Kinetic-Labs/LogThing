package com.github.kinetic.logthing.config;

import com.github.kinetic.logthing.util.io.log.LogUtil;

/**
 * Abstract class to create a config parser
 */
public abstract class AbstractConfigParser {

    private final String path;
    protected LogUtil log;

    /**
     * Create a new {@link AbstractConfigParser}
     *
     * @param path the path to config to be parsed
     */
    public AbstractConfigParser(final String path) {
        this.path = path;
        this.log = new LogUtil();
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
