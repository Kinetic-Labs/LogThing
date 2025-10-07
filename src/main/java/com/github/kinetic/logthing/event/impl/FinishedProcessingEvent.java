package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.util.types.ParsedLog;

/**
 * Dispatched when the log is finished being processed
 */
public final class FinishedProcessingEvent extends Event {
    private final String name;
    private final ParsedLog parsedLog;

    public FinishedProcessingEvent(final String name, final ParsedLog parsedLog) {
        this.name = name;
        this.parsedLog = parsedLog;
    }

    public String getName() {
        return name;
    }

    public ParsedLog getParsedLog() {
        return parsedLog;
    }
}
