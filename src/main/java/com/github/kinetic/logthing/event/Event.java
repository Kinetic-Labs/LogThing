package com.github.kinetic.logthing.event;

/**
 * An event
 */
@SuppressWarnings("unused")
public class Event {

    /**
     * Whether the event has been canceled
     */
    private boolean canceled;

    /**
     * Whether the event is a pre-event or a post-event
     */
    private boolean pre = true;

    /**
     * Get whether the event has been canceled
     *
     * @return true if canceled, false otherwise
     */
    public final boolean isCanceled() {
        return canceled;
    }

    /**
     * Get whether the event is a pre event or a post event
     *
     * @return true if pre, false if post
     */
    public final boolean isPre() {
        return pre;
    }

    /**
     * Get whether the event is a post event or a pre event
     *
     * @return true if post, false if pre
     */
    public final boolean isPost() {
        return !pre;
    }

    /**
     * Set the event to be a post event
     */
    public final void setPost() {
        pre = false;
    }

    /**
     * Cancel the event
     */
    public final void cancel() {
        canceled = true;
    }
}