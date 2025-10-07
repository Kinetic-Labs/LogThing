package com.github.kinetic.logthing.event.pool;

import com.github.kinetic.logthing.event.Event;
import com.github.kinetic.logthing.event.IEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("unchecked")
public final class EventSubscriberPool {

    private final Map<Type, List<EventSubscriber<?>>> subscriberCacheMap = new HashMap<>();

    /**
     * Subscribe to an event
     *
     * @param subscriber the class subscribing to the event
     */
    public void subscribe(final Object subscriber) {
        for(final Field field : subscriber.getClass().getDeclaredFields()) {
            if(field.getType().isAssignableFrom(IEventListener.class)) {
                if(!field.canAccess(subscriber))
                    field.setAccessible(true);

                try {
                    final IEventListener<Event> listener = (IEventListener<Event>) field.get(subscriber);

                    if(listener != null) {
                        final Type type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        subscriberCacheMap.computeIfAbsent(type, _ -> new CopyOnWriteArrayList<>()).add(new EventSubscriber<>(subscriber, listener));
                    }
                } catch(final IllegalAccessException illegalAccessException) {
                    throw new RuntimeException(illegalAccessException);
                }
            }
        }
    }

    /**
     * Unsubscribe from an event
     *
     * @param subscriber the class to be unsubscribed
     */
    public void unsubscribe(final Object subscriber) {
        subscriberCacheMap.values().forEach(eventSubscriberList ->
                eventSubscriberList.removeIf(eventSubscriber ->
                        eventSubscriber.subscriber().equals(subscriber)));
    }

    /**
     * Dispatch an event
     *
     * @param event the event to dispatch
     */
    public void dispatch(final Event event) {
        final List<EventSubscriber<?>> eventSubscriberList = subscriberCacheMap.get(event.getClass());

        if(eventSubscriberList != null && !eventSubscriberList.isEmpty()) {
            eventSubscriberList.forEach(eventSubscriber -> {
                final EventSubscriber<Event> subscriber = (EventSubscriber<Event>) eventSubscriber;

                subscriber.eventListener().invoke(event);
            });
        }
    }
}
