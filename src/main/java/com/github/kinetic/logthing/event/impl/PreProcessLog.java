package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.utils.types.Log;

public class PreProcessLog extends Event {
    private final Log log;

    public PreProcessLog(Log log) {
        this.log = log;
    }

    public Log getLog() {
        return log;
    }
}
