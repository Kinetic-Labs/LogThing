package com.github.kinetic.logthing.features.database;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.types.ParsedLog;

@SuppressWarnings("unused")
public final class Database {

    public Database() {
    }

    /**
     * Stores a ParsedLog object in the binary database file.
     * This method appends the object to the file.
     *
     * @param log The ParsedLog object to store.
     */
    public void storeLog(final ParsedLog log, final LogUtil logUtil) {
        // todo: implement db
    }
}
