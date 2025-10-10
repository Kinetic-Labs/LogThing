package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.config.impl.MiscSettings;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.util.Util;

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

    public <T extends Module> void disableModule(final Class<T> moduleClass) {
        final MiscSettings moduleSettings = LogThing.getInstance().getModuleSettings();

        LogThing.getInstance().getModuleRepository().getModule(moduleClass).setEnabled(false);

        moduleSettings.add(moduleClass.getSimpleName(), "enabled", "false");
    }

    public <T extends Module> void setModuleState(final String state, final Class<T> moduleClass) {
        switch(state.toLowerCase()) {
            case "true":
                enableModule(moduleClass);
                break;
            case "false":
                disableModule(moduleClass);
                break;
            default:
                log.error("Unknown module state: " + state);
        }
    }
}
