package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.sun.net.httpserver.HttpExchange;

/**
 * Dispatched when a network request comes in
 */
public final class RequestReceivedEvent extends Event {
    private final HttpExchange exchange;

    public RequestReceivedEvent(final HttpExchange exchange) {
        this.exchange = exchange;
    }

    public HttpExchange getExchange() {
        return exchange;
    }
}
