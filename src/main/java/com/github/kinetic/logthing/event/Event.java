package com.github.kinetic.logthing.event;

@SuppressWarnings("unused")
public class Event {

    private boolean canceled;
    private boolean pre = true;

    public final boolean isCanceled() {
        return canceled;
    }

    public final boolean isPre() {
        return pre;
    }

    public final boolean isPost() {
        return !pre;
    }

    public final void setPost() {
        pre = false;
    }

    public final void cancel() {
        canceled = true;
    }
}