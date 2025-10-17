package com.github.kinetic.logthing.config.flags;

/**
 * Flags for the config
 */
@SuppressWarnings("unused")
public final class LogThingFlags {

    /**
     * Flags
     */
    private static boolean debug;
    private static boolean help;

    /**
     * The LogThingFlags instance
     */
    private static final LogThingFlags INSTANCE = new LogThingFlags();

    /**
     * Get the debug flag
     *
     * @return true if debug is enabled
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Set the debug flag
     *
     * @param newDebug the new debug flag
     */
    public void setDebug(boolean newDebug) {
        debug = newDebug;
    }

    /**
     * Get the LogThingFlags instance
     *
     * @return the LogThingFlags instance
     */
    public static LogThingFlags getInstance() {
        return INSTANCE;
    }

    /**
     * Get the help flag
     *
     * @return true if help is enabled
     */
    public boolean getHelp() {
        return help;
    }

    /**
     * Set the help flag
     *
     * @param newHelp the new help flag
     */
    public void setHelp(boolean newHelp) {
        help = newHelp;
    }
}
