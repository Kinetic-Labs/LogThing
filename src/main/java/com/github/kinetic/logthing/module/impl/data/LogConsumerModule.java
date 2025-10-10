package com.github.kinetic.logthing.module.impl.data;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.features.storage.impl.memory.LogStorage;
import com.github.kinetic.logthing.module.ModuleRepository;
import com.github.kinetic.logthing.module.impl.io.LogServiceModule;
import com.github.kinetic.logthing.util.types.ParsedLog;

/**
 * Handles the final, processed log (e.g., store it in a database)
 */
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

        LogStorage.getInstance().insert(parsedLog);
        log.debug("Stored log.");
    };

    @Override
    protected void onEnable() {
        final ModuleRepository moduleRepository = LogThing.getInstance().getModuleRepository();
        final LogServiceModule logServiceModule = moduleRepository.getModule(LogServiceModule.class);

        LogStorage.getInstance().init();
        logServiceModule.rescan();

        super.onEnable();
    }

    @Override
    protected void onDisable() {
        LogStorage.getInstance().destroy();

        super.onDisable();
    }
}
