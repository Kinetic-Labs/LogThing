package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

@SuppressWarnings("unused")
public class StringUtils extends Utils {
    public StringUtils() {
        super("stringutils");
    }

    public String indent(String message, int indent) {
        StringBuilder stringBuilder = new StringBuilder();

        if(message == null)
            return stringBuilder.toString();

        stringBuilder.append("  ".repeat(Math.max(0, indent)));

        return stringBuilder.toString();
    }

    public String indent(int indent) {
        return "  ".repeat(Math.max(0, indent));
    }
}
