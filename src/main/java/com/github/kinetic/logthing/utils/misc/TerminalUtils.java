package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

import java.io.IOException;
import java.util.Objects;

public class TerminalUtils extends Utils {
    public TerminalUtils() {
        super("terminal");
    }

    public final String getTerminal() {
        return System.getenv("TERM");
    }

    public final boolean isIntellijDebug() {
        return Objects.equals(getTerminal(), "java");
    }

    public final void disableControlEcho() {
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

    public final void enableControlEcho() {
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
