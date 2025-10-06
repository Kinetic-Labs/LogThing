package com.github.kinetic.logthing.module.impl.data;

import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.storage.LogStorage;
import com.github.kinetic.logthing.utils.types.ParsedLog;

public final class LogConsumerModule extends Module {
    public LogConsumerModule() {
        super("LogConsumerModule", "LCM", "Handles automated database actions", Category.DATA);
    }

    @SuppressWarnings("unused")
    private final IEventListener<FinishedProcessingEvent> eventListener = event -> {
        Thread.currentThread().setName(getThreadName());

        final String name = event.getName();
        final ParsedLog parsedLog = event.getParsedLog();

        if(name == null) {
            log.warn("Could not get name for log!");

            return;
        }

        log.debug("Finished processing log: " + name);

        LogStorage.getInstance().addLog(parsedLog);
        log.debug("Stored log.");
    };
}
