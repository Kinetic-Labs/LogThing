package com.github.kinetic.logthing.features.storage.impl.memory;

import com.github.kinetic.logthing.features.storage.Storage;
import com.github.kinetic.logthing.util.types.ParsedLog;

import java.util.*;

/**
 * In-memory storage for logs
 */
public final class LogStorage implements Storage {

    /**
     * LogUtil instance
     */
    private static final LogStorage INSTANCE = new LogStorage();

    /**
     * The logs stored in storage
     */
    private final Set<ParsedLog> logs = new HashSet<>();

    /**
     * Private constructor
     */
    private LogStorage() {
        init();
    }

    /**
     * @return a {@link LogStorage} instance
     */
    public static LogStorage getInstance() {
        return INSTANCE;
    }

    /**
     * Insert a log to storage
     *
     * @param theLog the log to store
     */
    public void insert(final ParsedLog theLog) {
        logs.add(theLog);
    }

    /**
     *
     * @return a {@link List} of logs from storage
     */
    public Set<ParsedLog> getLogs() {
        return logs;
    }

    /**
     * Initialize the {@link LogStorage}
     */
    public void init() {
        log.debug("Initializing LogStorage...");

        this.logs.clear();
    }

    /**
     * De-initialize the {@link LogStorage}
     */
    public void destroy() {
        log.debug("De-initializing LogStorage...");

        this.logs.clear();
    }
}
