package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

import java.io.IOException;

public final class TerminalUtils implements Utils {

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
        } catch(IOException | InterruptedException _) {
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
        } catch(final IOException | InterruptedException _) {
        }
    }
}
