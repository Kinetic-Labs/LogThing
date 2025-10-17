package com.github.kinetic.logthing.event;

import com.github.kinetic.logthing.event.pool.EventSubscriberPool;
import com.github.kinetic.logthing.util.io.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Event bus for dispatching events
 */
@SuppressWarnings({"unchecked", "unused"})
public final class EventBus {

    /**
     * LogUtil instance
     */
    private final LogUtil log = new LogUtil();

    /**
     * List of listeners
     */
    private final List<IEventListener<?>> listeners = new ArrayList<>();

    /**
     * Event subscriber pool
     */
    private final EventSubscriberPool eventSubscriberPool;

    /**
     * Create a new event bus
     */
    public EventBus() {
        this.eventSubscriberPool = new EventSubscriberPool();
    }

    /**
     * Register a listener
     *
     * @param listener the listener to register
     * @param <T>      the type of event the listener handles
     */
    public <T> void register(final IEventListener<T> listener) {
        listeners.add(listener);
    }

    /**
     * Dispatch an event
     *
     * @param event the event to dispatch
     * @param <T>   the type of event to dispatch
     */
    public <T> void dispatch(final T event) {
        log.debug("EventBus: Dispatching event: " + event);

        for(final IEventListener<?> listener : listeners) {
            if(listener != null)
                ((IEventListener<T>) listener).invoke(event);
        }
    }

    /**
     * Subscribe to an event
     *
     * @param subscriber the class to be subscribed
     */
    public void subscribe(final Object subscriber) {
        log.debug("EventBus: Subscribe: " + subscriber);
        eventSubscriberPool.subscribe(subscriber);
    }

    /**
     * Unsubscribe from an event
     *
     * @param subscriber the class to be unsubscribed
     */
    public void unsubscribe(final Object subscriber) {
        log.debug("EventBus: Unsubscribe: " + subscriber);
        eventSubscriberPool.unsubscribe(subscriber);
    }

    /**
     * Dispatch an event
     *
     * @param event the event to dispatch
     */
    public void dispatch(final Event event) {
        eventSubscriberPool.dispatch(event);
    }
}
