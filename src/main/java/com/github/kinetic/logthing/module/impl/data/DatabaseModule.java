package com.github.kinetic.logthing.module.impl.data;

import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.StoreDatabaseEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.features.database.Database; // <--- IMPORT DATABASE
import com.github.kinetic.logthing.util.types.ParsedLog;

public class DatabaseModule extends Module {

    private final Database database = new Database();

    public DatabaseModule() {
        super("DatabaseModule", "DBM", "Handles database interactions", Category.DATA);
    }

    @SuppressWarnings("unused")
    private final IEventListener<StoreDatabaseEvent> storeDatabaseEvent = event -> {
        final ParsedLog parsedLog = event.getLog();

        // log.debug("DBM: Attempting to store log: " + parsedLog.tag() + " - " + parsedLog.message());

        database.storeLog(parsedLog, log);
        // log.debug("DBM: Log successfully stored.");
    };
}
