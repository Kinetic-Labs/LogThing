package com.github.kinetic.logthing.module.impl.misc;

import com.github.kinetic.logthing.event.IEventListener;
import com.github.kinetic.logthing.event.impl.RequestReceivedEvent;
import com.github.kinetic.logthing.module.Category;
import com.github.kinetic.logthing.module.Module;

/**
 * Logs incoming network requests
 */
public final class RequestLoggerModule extends Module {

    /**
     * Create a new {@link RequestLoggerModule}
     */
    public RequestLoggerModule() {
        super("RequestLoggerModule", "RLM", "Logs all incoming requests", Category.MISC);
    }

    /**
     * The event listener for {@link RequestReceivedEvent}
     */
    @SuppressWarnings("unused")
    private final IEventListener<RequestReceivedEvent> requestReceivedEvent = event -> {
        Thread.currentThread().setName(getThreadName());

        String message = String.format(
                "[%s] Request received from %s: %s",
                event.getExchange().getRequestMethod(),
                event.getExchange().getRemoteAddress(),
                event.getExchange().getRequestURI()
        );

        log.info(message);
    };
}
