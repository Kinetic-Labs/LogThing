package com.github.kinetic.logthing.utils.misc;

import java.util.Objects;

public class Terminal {
    public Terminal() {
    }

    public String getTerminal() {
        return System.getenv("TERM");
    }

    public boolean isIntellijDebug() {
        return Objects.equals(getTerminal(), "java");
    }
}
