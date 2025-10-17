package com.github.kinetic.logthing.module;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.misc.ConfigUtil;
import com.github.kinetic.logthing.util.misc.StringUtil;

/**
 * Abstract class for modules
 */
@SuppressWarnings("unused")
public abstract class Module {

    /**
     * Module name
     */
    private final String name;

    /**
     * The name of the thread the module uses
     */
    private final String threadName;

    /**
     * The description of the module
     */
    private final String description;

    /**
     * The category of the module
     */
    private final Category category;

    /**
     * Whether the module is enabled or not
     */
    private boolean enabled;

    /**
     * Utilities
     */
    protected final LogUtil log = new LogUtil();
    protected final StringUtil stringUtil = new StringUtil();
    protected final ConfigUtil configUtil = new ConfigUtil();

    /**
     * Padding
     */
    private final String padding = stringUtil.indent(3);

    /**
     * Create a new {@link Module}
     *
     * @param name        the name of module
     * @param threadName  the name of its thread
     * @param description the description of module
     * @param category    the category of module
     */
    protected Module(final String name, final String threadName, final String description, final Category category) {
        this.name = name;
        this.threadName = threadName;
        this.description = description;
        this.category = category;
    }

    /**
     * Get of the {@link Module}
     *
     * @return the module's name
     */
    public final String getName() {
        return name;
    }

    /**
     * Get the thread name of the {@link Module}
     *
     * @return the thread name the module uses
     */
    public final String getThreadName() {
        return threadName;
    }

    /**
     * Get the description of the {@link Module}
     *
     * @return the description of the module
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Get the module's category
     *
     * @return the category of the module
     */
    public final Category getCategory() {
        return category;
    }

    /**
     * Checks if the module is enabled and returns a boolean
     *
     * @return true if enabled, false if disabled
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Set a module to be enabled or disabled
     *
     * @param enabled true to enable and false to disable
     */
    public final void setEnabled(final boolean enabled) {
        this.enabled = enabled;

        if(enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Toggle a module
     *
     * <p>
     * If the module is enabled, it will be disabled and vice versa
     * </p>
     */
    public final void toggle() {
        setEnabled(!enabled);
    }

    /**
     * Subscribes a module to the {@link com.github.kinetic.logthing.event.EventBus} and logs that it was enabled
     */
    protected void onEnable() {
        LogThing.getInstance().getEventBus().subscribe(this);

        log.debug(padding + "> Started " + this.getName() + ".");
    }

    /**
     * Unsubscribes a module from the {@link com.github.kinetic.logthing.event.EventBus} and logs that it was stopped
     */
    protected void onDisable() {
        LogThing.getInstance().getEventBus().unsubscribe(this);

        log.debug(padding + "> Stopped " + this.getName() + ".");
    }
}
