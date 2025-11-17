package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.util.types.ParsedLog;

public class StoreDatabaseEvent extends Event {

    private final ParsedLog log;

    public StoreDatabaseEvent(final ParsedLog log) {
        this.log = log;
    }

    public ParsedLog getLog() {
        return this.log;
    }
}
