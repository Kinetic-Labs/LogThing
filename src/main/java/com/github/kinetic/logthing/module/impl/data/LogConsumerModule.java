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
    private final IEventListener<FinishedProcessingEvent> eventListener = event -> log.debug("Finished processing: " + event.getName());
}
