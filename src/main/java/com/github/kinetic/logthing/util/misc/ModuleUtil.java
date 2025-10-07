package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.LogThing;
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
        LogThing.getInstance().getModuleRepository().getModule(moduleClass).setEnabled(true);
    }

    public <T extends Module> void disableModule(final Class<T> moduleClass) {
        LogThing.getInstance().getModuleRepository().getModule(moduleClass).setEnabled(false);
    }
}
