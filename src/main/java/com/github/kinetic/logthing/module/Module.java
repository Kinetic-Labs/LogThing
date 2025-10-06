package com.github.kinetic.logthing.module;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.misc.StringUtils;

@SuppressWarnings("unused")
public abstract class Module {
    private final String name;
    private final String threadName;
    private final String description;
    private final Category category;
    private boolean enabled;
    protected final LogUtils log = new LogUtils();
    protected final StringUtils stringUtils = new StringUtils();
    private final String padding = stringUtils.indent(3);

    protected Module(final String name, final String threadName, final String description, final Category category) {
        this.name = name;
        this.threadName = threadName;
        this.description = description;
        this.category = category;
    }

    public final String getName() {
        return name;
    }

    public final String getThreadName() {
        return threadName;
    }

    public final String getDescription() {
        return description;
    }

    public final Category getCategory() {
        return category;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if(enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public final void toggle() {
        setEnabled(!enabled);
    }

    protected void onEnable() {
        LogThing.getInstance().getEventBus().subscribe(this);

        log.debug(padding + "> Started " + this.getName() + ".");
    }

    protected void onDisable() {
        LogThing.getInstance().getEventBus().unsubscribe(this);

        log.debug(padding + "> Stopped " + this.getName() + ".");
    }
}
