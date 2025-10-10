package com.github.kinetic.logthing.event;

/**
 * Storage for events
 *
 * @param owner    the owner of the event
 * @param callback callback for event
 * @param <T>      the event
 */
@SuppressWarnings("unused")
public record EventStorage<T>(Object owner, IEventListener<T> callback) {

}
