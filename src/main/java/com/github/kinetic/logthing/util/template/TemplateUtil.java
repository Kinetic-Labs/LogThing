package com.github.kinetic.logthing.util.template;

import com.github.kinetic.logthing.util.Util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record TemplateUtil(String template) implements Util {

    public String process(final HashMap<String, String> variables) {
        final StringBuilder stringBuilder = new StringBuilder();
        final Pattern pattern = Pattern.compile("\\{#\\s*([a-zA-Z0-9_]+)\\s*#}");
        final Matcher matcher = pattern.matcher(template);

        while(matcher.find()) {
            final String varName = matcher.group(1);
            final String replacement = variables.get(varName);

            if(replacement != null) {
                matcher.appendReplacement(stringBuilder, Matcher.quoteReplacement(replacement));
            } else {
                matcher.appendReplacement(stringBuilder, matcher.group(0));
            }
        }

        matcher.appendTail(stringBuilder);

        return stringBuilder.toString();
    }
}
