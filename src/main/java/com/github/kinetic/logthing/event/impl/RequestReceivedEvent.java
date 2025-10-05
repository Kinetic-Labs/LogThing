package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.sun.net.httpserver.HttpExchange;

public final class RequestReceivedEvent extends Event {
    private final HttpExchange exchange;

    public RequestReceivedEvent(final HttpExchange exchange) {
        this.exchange = exchange;
    }

    public HttpExchange getExchange() {
        return exchange;
    }
}
