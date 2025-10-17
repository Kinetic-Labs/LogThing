package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.util.types.ParsedLog;

/**
 * Dispatched when the log is finished being processed
 */
public final class FinishedProcessingEvent extends Event {

    /**
     * The name of the log that was processed.
     */
    private final String name;

    /**
     * The parsed log.
     */
    private final ParsedLog parsedLog;

    /**
     * Create a new {@link FinishedProcessingEvent}
     *
     * @param name      the name of the log that was processed
     * @param parsedLog the parsed log
     */
    public FinishedProcessingEvent(final String name, final ParsedLog parsedLog) {
        this.name = name;
        this.parsedLog = parsedLog;
    }

    /**
     * Get the name of the log that was processed.
     *
     * @return the name of the log that was processed
     */
    public String getName() {
        return name;
    }

    /**
     * Get the parsed log.
     *
     * @return the parsed log
     */
    public ParsedLog getParsedLog() {
        return parsedLog;
    }
}
