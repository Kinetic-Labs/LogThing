package com.github.kinetic.logthing.event.pool;

import com.github.kinetic.logthing.event.IEventListener;

public record EventSubscriber<Event>(Object subscriber, IEventListener<Event> eventListener) {

}
