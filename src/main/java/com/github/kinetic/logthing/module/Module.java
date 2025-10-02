package com.github.kinetic.logthing.module;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.misc.StringUtils;

@SuppressWarnings("unused")
public abstract class Module {
    private final String name;
    private final String displayName;
    private final String description;
    private final Category category;
    private boolean enabled;
    protected final LogUtils log = new LogUtils();
    protected final StringUtils stringUtils = new StringUtils();
    private final String padding = stringUtils.indent(3);

    protected Module(String name, String displayName, String description, Category category) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.category = category;
    }

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    protected void onEnable() {
        LogThing.getEventBus().subscribe(this);

        log.debug(padding + "> Started " + this.getName() + ".");
    }

    protected void onDisable() {
        LogThing.getEventBus().unsubscribe(this);

        log.debug(padding + "> Stopped " + this.getName() + ".");
    }
}
