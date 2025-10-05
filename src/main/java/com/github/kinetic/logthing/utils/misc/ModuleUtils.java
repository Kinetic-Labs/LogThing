package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.LogThing;
import com.github.kinetic.logthing.module.Module;
import com.github.kinetic.logthing.utils.Utils;

@SuppressWarnings("unused")
public final class ModuleUtils implements Utils {

    public <T extends Module> void enableModule(final Class<T> moduleClass) {
        LogThing.getInstance().getModuleRepository().getModule(moduleClass).setEnabled(true);
    }

    public <T extends Module> void disableModule(final Class<T> moduleClass) {
        LogThing.getInstance().getModuleRepository().getModule(moduleClass).setEnabled(false);
    }
}
