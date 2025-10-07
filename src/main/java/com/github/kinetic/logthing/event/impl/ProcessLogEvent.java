package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.util.types.Log;

/**
 * Dispatched when a log is ready to be processed
 */
public final class ProcessLogEvent extends Event {
    private final Log log;

    public ProcessLogEvent(final Log log) {
        this.log = log;
    }

    public Log getLog() {
        return log;
    }
}
