package com.github.kinetic.logthing.features.processor.impl;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.type.LogThingConfig;
import com.github.kinetic.logthing.features.processor.AbstractProcessor;
import com.github.kinetic.logthing.util.types.ParsedLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataExtractorProcessor extends AbstractProcessor {

    public DataExtractorProcessor(final String rawLog) {
        super(rawLog);
    }

    @Override
    public ParsedLog process() {
        final LogThingConfig config = LogThing.getInstance().getLogThingConfig();

        final String timestampPattern = config.processorKey().timestampPattern();
        final String tagPattern = config.processorKey().tagPattern();
        final String levelPattern = config.processorKey().processorLevelPattern();
        final String fullPattern = String.format("^%s\\s+%s\\s+%s\\s+(.*)$",
                timestampPattern, tagPattern, levelPattern);
        final Pattern pattern = Pattern.compile(fullPattern);
        final Matcher matcher = pattern.matcher(rawLog);

        final String timestamp, tag, level, message;

        if(matcher.find()) {
            timestamp = matcher.group(1);
            tag = matcher.group(2);
            level = matcher.group(3);
            message = matcher.group(4);
        } else {
            final Pattern simplePattern = Pattern.compile(
                    String.format("^(%s)\\s+(.*)$", levelPattern));
            final Matcher simpleMatcher = simplePattern.matcher(rawLog);

            if(simpleMatcher.find()) {
                timestamp = null;
                tag = null;
                level = simpleMatcher.group(1);
                message = simpleMatcher.group(2);
            } else {
                timestamp = null;
                tag = null;
                level = "UNKNOWN";
                message = rawLog;
            }
        }

        return new ParsedLog(timestamp, tag, level, message);
    }
}
