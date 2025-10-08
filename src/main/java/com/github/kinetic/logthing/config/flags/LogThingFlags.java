package com.github.kinetic.logthing.config.flags;

@SuppressWarnings("unused")
public final class LogThingFlags {

    private static boolean debug;
    private static boolean help;
    private static final LogThingFlags INSTANCE = new LogThingFlags();

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean newDebug) {
        debug = newDebug;
    }

    public static LogThingFlags getInstance() {
        return INSTANCE;
    }

    public boolean getHelp() {
        return help;
    }

    public void setHelp(boolean newHelp) {
        help = newHelp;
    }
}
