package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.sun.net.httpserver.HttpExchange;

/**
 * Dispatched when a network request comes in
 */
public final class RequestReceivedEvent extends Event {

    /**
     * The exchange of the request
     */
    private final HttpExchange exchange;

    /**
     * Create a new {@link RequestReceivedEvent}
     *
     * @param exchange the exchange of the request
     */
    public RequestReceivedEvent(final HttpExchange exchange) {
        this.exchange = exchange;
    }

    /**
     * Get the exchange of the request
     *
     * @return the exchange of the request
     */
    public HttpExchange getExchange() {
        return exchange;
    }
}
