package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.impl.MiscSettings;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.util.Util;

/**
 * Utilities for working with modules
 */
@SuppressWarnings("unused")
public final class ModuleUtil implements Util {

    /**
     * Enable a module
     *
     * @param moduleClass module to be enabled
     * @param <T>         any class extending Module
     */
    public <T extends Module> void enableModule(final Class<T> moduleClass) {
        final MiscSettings moduleSettings = LogThing.getInstance().getModuleSettings();

        LogThing.getInstance().getModuleRepository().getModule(moduleClass).setEnabled(true);

        moduleSettings.add(moduleClass.getSimpleName(), "enabled", "true");
    }

    /**
     * Disable a module
     *
     * @param moduleClass module to be disabled
     * @param <T>         any class extending Module
     */
    public <T extends Module> void disableModule(final Class<T> moduleClass) {
        final MiscSettings moduleSettings = LogThing.getInstance().getModuleSettings();

        LogThing.getInstance().getModuleRepository().getModule(moduleClass).setEnabled(false);

        moduleSettings.add(moduleClass.getSimpleName(), "enabled", "false");
    }

    /**
     * Set the state of a module
     *
     * <p>
     * Should *NOT* be used outside the flag system
     * as it takes lose boolean values of "true" and "false"
     * can cause problems if used incorrectly
     * use {@link #enableModule(Class)} and {@link #disableModule(Class)} when possible
     * </p>
     *
     * @param state       true or false
     * @param moduleClass module to be set to state
     * @param <T>         any class extending Module
     */
    public <T extends Module> void setModuleState(final String state, final Class<T> moduleClass) {
        switch(state.toLowerCase()) {
            case "true": {
                enableModule(moduleClass);
                break;
            }

            case "false": {
                disableModule(moduleClass);
                break;
            }

            default: {
                log.error("Unknown module state: " + state);
            }
        }
    }
}
