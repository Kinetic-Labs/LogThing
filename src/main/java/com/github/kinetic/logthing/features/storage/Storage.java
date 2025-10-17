package com.github.kinetic.logthing.features.storage;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.types.ParsedLog;

import java.util.Set;

/**
 * Interface for storage classes
 */
public interface Storage {

    /**
     * Utilities
     */
    LogUtil log = new LogUtil();

    /**
     * Get all stored logs
     * @return the stored logs
     */
    Set<ParsedLog> getLogs();

    /**
     * Insert a log into the storage
     * @param log the log to insert
     */
    void insert(final ParsedLog log);

    /**
     * Initialize the storage class
     */
    void init();

    /**
     * Destroy the storage class
     */
    void destroy();
}
