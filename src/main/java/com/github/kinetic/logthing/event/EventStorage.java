/*
 * Copyright (c) Alya Client 2024-2025.
 *
 * This file belongs to Alya Client,
 * an open-source Fabric injection client.
 * Rye GitHub: https://github.com/AlyaClient/alya-beta.git
 *
 * THIS PROJECT DOES NOT HAVE A WARRANTY.
 *
 * Alya (and subsequently, its files) are all licensed under the MIT License.
 * Alya should have come with a copy of the MIT License.
 * If it did not, you may obtain a copy here:
 * MIT License: https://opensource.org/license/mit
 *
 */

package com.github.kinetic.logthing.event;

@SuppressWarnings("unused")
public class EventStorage<T> {

    private final Object owner;
    private final IEventListener<T> callback;

    public EventStorage(Object owner, IEventListener<T> callback) {
        this.owner = owner;
        this.callback = callback;
    }

    public Object getOwner() {
        return owner;
    }

    public IEventListener<T> getCallback() {
        return callback;
    }

}
