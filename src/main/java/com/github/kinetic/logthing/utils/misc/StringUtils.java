package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

@SuppressWarnings("unused")
public final class StringUtils implements Utils {

    /**
     * Indent a string by x tabs
     * @param string string to be indented
     * @param indent the amount to indent
     * @return the indented string
     */
    public String indent(String string, int indent) {
        final StringBuilder stringBuilder = new StringBuilder();

        if(string == null)
            return stringBuilder.toString();

        stringBuilder.append("  ".repeat(Math.max(0, indent)));

        return stringBuilder.toString();
    }

    /**
     * Overloaded of indent without string param
     * @param indent amount to indent
     * @return the indented whitespace
     */
    public String indent(int indent) {
        return "  ".repeat(Math.max(0, indent));
    }
}
