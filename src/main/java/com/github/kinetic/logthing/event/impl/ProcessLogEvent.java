package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.utils.types.Log;

public final class ProcessLogEvent extends Event {
    private final Log log;

    public ProcessLogEvent(final Log log) {
        this.log = log;
    }

    public Log getLog() {
        return log;
    }
}
