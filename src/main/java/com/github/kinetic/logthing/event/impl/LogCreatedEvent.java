package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;

import java.nio.file.Path;

public final class LogCreatedEvent extends Event {

    private final Path path;

    public LogCreatedEvent(final Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
