package com.github.kinetic.logthing.storage;

import com.github.kinetic.logthing.utils.types.ParsedLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LogStorage {

    private static final LogStorage INSTANCE = new LogStorage();
    private final List<ParsedLog> logs = new ArrayList<>();

    private LogStorage() {
    }

    /**
     * @return a {@link LogStorage} instance
     */
    public static LogStorage getInstance() {
        return INSTANCE;
    }

    /**
     * Insert a log to data
     *
     * @param log the log to store
     */
    public void addLog(final ParsedLog log) {
        logs.add(log);
    }

    /**
     *
     * @return a {@link List} of logs
     */
    public List<ParsedLog> getLogs() {
        return Collections.unmodifiableList(logs);
    }
}
