package com.github.kinetic.logthing.event.impl;

import com.github.kinetic.logthing.event.Event;
import com.sun.net.httpserver.HttpExchange;

public class RequestReceivedEvent extends Event {
    private final HttpExchange exchange;

    public RequestReceivedEvent(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public final HttpExchange getExchange() {
        return exchange;
    }
}
