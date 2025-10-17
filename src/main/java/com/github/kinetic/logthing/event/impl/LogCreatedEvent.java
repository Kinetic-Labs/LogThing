package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;

import java.nio.file.Path;

/**
 * Dispatched when a new log is created
 */
public final class LogCreatedEvent extends Event {

    /**
     * The path of the new log
     */
    private final Path path;

    /**
     * Create a new {@link LogCreatedEvent}
     *
     * @param path the path of the new log
     */
    public LogCreatedEvent(final Path path) {
        this.path = path;
    }

    /**
     * Get the path of the new log
     *
     * @return the path of the new log
     */
    public Path getPath() {
        return path;
    }
}
