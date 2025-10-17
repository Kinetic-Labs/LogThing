package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.util.types.Log;

/**
 * Dispatched when a log is ready to be processed
 */
public final class ProcessLogEvent extends Event {

    /**
     * The log to be processed
     */
    private final Log log;

    /**
     * Create a new {@link ProcessLogEvent}
     *
     * @param log the log to be processed
     */
    public ProcessLogEvent(final Log log) {
        this.log = log;
    }

    /**
     * Get the log to be processed
     *
     * @return the log to be processed
     */
    public Log getLog() {
        return log;
    }
}
