package com.github.kinetic.logthing.event;

/**
 * {@link IEventListener interface for implementing an event listenr in a class}
 *
 * @param <Event> the event to listen for
 */
public interface IEventListener<Event> {
    void invoke(final Event event);
}
