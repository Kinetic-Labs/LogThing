package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

import java.io.IOException;
import java.util.Objects;

public final class TerminalUtils implements Utils {

    public String getTerminal() {
        return System.getenv("TERM");
    }

    public boolean isIntellijDebug() {
        return Objects.equals(getTerminal(), "java");
    }

    public void disableControlEcho() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();

            if(!os.contains("win") && isIntellijDebug()) {
                new ProcessBuilder("stty", "-echoctl")
                        .inheritIO()
                        .start()
                        .waitFor();
            }
        } catch(IOException | InterruptedException _) {
        }
    }

    public void enableControlEcho() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();

            if(!os.contains("win") && isIntellijDebug()) {
                new ProcessBuilder("stty", "echoctl")
                        .inheritIO()
                        .start()
                        .waitFor();
            }
        } catch(final IOException | InterruptedException _) {
        }
    }
}
