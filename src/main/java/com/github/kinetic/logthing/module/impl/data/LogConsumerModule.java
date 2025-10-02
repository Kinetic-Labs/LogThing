package com.github.kinetic.logthing.module.impl.data;

import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.FinishedProcessingEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;

public class LogConsumerModule extends Module {
    public LogConsumerModule() {
        super("LogConsumerModule", "Handles automated database actions", Category.DATA);
    }

    @SuppressWarnings("unused")
    private final IEventListener<FinishedProcessingEvent> eventListener = event -> {
        if(event.getName() == null) {
            log.warn("Could not get name for log!");

            return;
        }

        String name = event.getName();

        log.debug("Finished processing log: " + name);
    };
}
