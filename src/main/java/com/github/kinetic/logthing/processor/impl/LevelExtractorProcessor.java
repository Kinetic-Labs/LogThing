package com.github.kinetic.logthing.processor.impl;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.processor.AbstractProcessor;
import com.github.kinetic.logthing.util.types.ParsedLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LevelExtractorProcessor extends AbstractProcessor {

    public LevelExtractorProcessor(final String rawLog) {
        super(rawLog);
    }

    @Override
    public ParsedLog process() {
        final String processorPattern = LogThing.getInstance().getLogThingConfig().processorPattern();
        final Pattern pattern = Pattern.compile(String.format("^(%s)\\s+(.*)$", processorPattern));
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
