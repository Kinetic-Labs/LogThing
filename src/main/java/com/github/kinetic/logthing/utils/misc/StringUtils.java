package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

@SuppressWarnings("unused")
public final class StringUtils implements Utils {

    public String indent(String message, int indent) {
        final StringBuilder stringBuilder = new StringBuilder();

        if(message == null)
            return stringBuilder.toString();

        stringBuilder.append("  ".repeat(Math.max(0, indent)));

        return stringBuilder.toString();
    }

    public String indent(int indent) {
        return "  ".repeat(Math.max(0, indent));
    }
}
