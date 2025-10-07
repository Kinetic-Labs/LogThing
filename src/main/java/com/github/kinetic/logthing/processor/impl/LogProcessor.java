package com.github.kinetic.logthing.processor.impl;

import com.github.kinetic.logthing.processor.AbstractProcessor;
import com.github.kinetic.logthing.util.types.ParsedLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogProcessor extends AbstractProcessor {

    public LogProcessor(final String rawLog) {
        super(rawLog);
    }

    @Override
    public ParsedLog process() {
        final Pattern pattern = Pattern.compile("^(INFO|WARNING|ERROR|DEBUG|TRACE|FATAL)\\s*(.*)$");
        final Matcher matcher = pattern.matcher(rawLog);
        final String level, message;

        if(matcher.find()) {
            level = matcher.group(1);
            message = matcher.group(2);
        } else {
            level = "UNKNOWN";
            message = rawLog;
        }

        return new ParsedLog(level, message);
    }
}
