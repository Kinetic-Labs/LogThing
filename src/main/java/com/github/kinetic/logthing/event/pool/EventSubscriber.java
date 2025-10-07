package com.github.kinetic.logthing.event.pool;

import com.github.kinetic.logthing.event.IEventListener;

/**
 * An event subscriber
 *
 * @param subscriber    the subscriber
 * @param eventListener the event listener
 * @param <Event>       the event being listened for
 */
public record EventSubscriber<Event>(Object subscriber, IEventListener<Event> eventListener) {

}
