package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.util.Util;

/**
 * Utility class for working with ANSI escape codes
 *
 * @param ansiString
 */
public record AnsiUtil(String ansiString) implements Util {

    /**
     * Strips ANSI escape codes from string
     *
     * @return the string without ANSI escape codes
     */
    public String stripAnsi() {
        return ansiString.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
