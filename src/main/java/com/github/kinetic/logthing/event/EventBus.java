package com.github.kinetic.logthing.event;

import com.github.kinetic.logthing.event.pool.EventSubscriberPool;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "unused"})
public final class EventBus {
    private final List<IEventListener<?>> listeners = new ArrayList<>();

    public <T> void register(final IEventListener<T> listener) {
        listeners.add(listener);
    }

    public <T> void dispatch(final T event) {
        for(final IEventListener<?> listener : listeners) {
            if(listener != null)
                ((IEventListener<T>) listener).invoke(event);
        }
    }

    private final EventSubscriberPool eventSubscriberPool;

    public EventBus() {
        this.eventSubscriberPool = new EventSubscriberPool();
    }

    public void subscribe(final Object subscriber) {
        eventSubscriberPool.subscribe(subscriber);
    }

    public void unsubscribe(final Object subscriber) {
        eventSubscriberPool.unsubscribe(subscriber);
    }

    public void dispatch(final Event event) {
        eventSubscriberPool.dispatch(event);
    }
}
