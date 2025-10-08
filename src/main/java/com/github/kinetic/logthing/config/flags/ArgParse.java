package com.github.kinetic.logthing.config.flags;

import com.github.kinetic.logthing.util.io.log.LogUtil;

@SuppressWarnings("SameParameterValue")
public final class ArgParse {

    private final LogUtil log = new LogUtil();
    private final LogThingFlags flags = LogThingFlags.getInstance();
    private final String extraFlagPrefix = "-X:";
    private final String commonFlagPrefix = "-LT:";

    private void ignoreArgument(final String arg, final String reason) {
        log.warn("Completely ignored argument: " + arg + " (" + reason + ")");
    }

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
