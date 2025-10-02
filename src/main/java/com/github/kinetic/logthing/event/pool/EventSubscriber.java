package com.github.kinetic.logthing.event.pool;

import com.github.kinetic.logthing.event.IEventListener;

public final class EventSubscriber<Event> {

    private final Object subscriber;
    private final IEventListener<Event> eventListener;

    public EventSubscriber(final Object subscriber, final IEventListener<Event> eventListener) {
        this.subscriber = subscriber;
        this.eventListener = eventListener;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public IEventListener<Event> getEventListener() {
        return eventListener;
    }
}
