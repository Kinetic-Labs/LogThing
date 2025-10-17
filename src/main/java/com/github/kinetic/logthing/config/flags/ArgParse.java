package com.github.kinetic.logthing.config.flags;

import com.github.kinetic.logthing.util.io.log.LogUtil;

/**
 * Parses command line flags
 */
@SuppressWarnings("SameParameterValue")
public final class ArgParse {

    /**
     * Utilities
     */
    private final LogUtil log = new LogUtil();
    private final LogThingFlags flags = LogThingFlags.getInstance();

    /**
     * Prefix for extra flags
     */
    private final String extraFlagPrefix = "-X:";

    /**
     * Prefix for common flags
     */
    private final String commonFlagPrefix = "-LT:";

    /**
     * Ignore an argument
     *
     * @param arg    argument to ignore
     * @param reason reason for ignoring
     */
    private void ignoreArgument(final String arg, final String reason) {
        log.warn("Completely ignored argument: " + arg + " (" + reason + ")");
    }

    /**
     * Parse a common flag
     *
     * @param args the array args to parse
     */
    private void parseCommonFlag(final String[] args) {
        for(final String arg : args) {
            final String name = arg.substring(commonFlagPrefix.length());

            switch(name) {
                case "help":
                    flags.setHelp(true);
                    break;
                case "default":
                    ignoreArgument(arg, "unknown argument");
                    break;
            }
        }
    }

    /**
     * Parse an extra flag
     *
     * @param args the array args to parse
     */
    private void parseExtraFlag(final String[] args) {
        for(final String arg : args) {
            final String name = arg.substring(extraFlagPrefix.length());

            switch(name) {
                case "debug":
                    flags.setDebug(true);
                    break;

                case "default":
                    ignoreArgument(arg, "unknown argument");
                    break;
            }
        }
    }

    /**
     * Parse command line flags
     *
     * @param args the array args to parse
     */
    public void parseArgs(final String[] args) {
        for(final String arg : args) {
            if(arg.startsWith(commonFlagPrefix))
                parseCommonFlag(args);

            if(arg.startsWith(extraFlagPrefix))
                parseExtraFlag(args);
        }
    }
}
