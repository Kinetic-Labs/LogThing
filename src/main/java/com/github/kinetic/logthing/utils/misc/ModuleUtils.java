package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.utils.Utils;

@SuppressWarnings("unused")
public class ModuleUtils extends Utils {
    public ModuleUtils() {
        super("module_utils");
    }

    public final <T extends Module> void enableModule(final Class<T> moduleClass) {
        LogThing.getModuleRepository().getModule(moduleClass).setEnabled(true);
    }

    public final <T extends Module> void disableModule(final Class<T> moduleClass) {
        LogThing.getModuleRepository().getModule(moduleClass).setEnabled(false);
    }
}
