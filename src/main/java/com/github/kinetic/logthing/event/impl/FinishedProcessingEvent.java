package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.utils.types.Log;

public class FinishedProcessingEvent extends Event {
    private final String name;
    private final Log log;

    public FinishedProcessingEvent(String name, Log log) {
        this.name = name;
        this.log = log;
    }

    public final String getName() {
        return name;
    }

    public final Log getLog() {
        return log;
    }
}
