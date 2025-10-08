package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.util.Util;

import java.io.IOException;

public final class TerminalUtil implements Util {

    /**
     * Disables control echo (e.g. CTRL+C)
     */
    public void disableControlEcho() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();

            if(!os.contains("win")) {
                new ProcessBuilder("stty", "-echoctl")
                        .inheritIO()
                        .start()
                        .waitFor();
            }
        } catch(final IOException | InterruptedException ignored) {
        }
    }

    /**
     * Re-enables control echo (e.g. CTRL + C)
     */
    public void enableControlEcho() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();

            if(!os.contains("win")) {
                new ProcessBuilder("stty", "echoctl")
                        .inheritIO()
                        .start()
                        .waitFor();
            }
        } catch(final IOException | InterruptedException ignored) {
        }
    }
}
