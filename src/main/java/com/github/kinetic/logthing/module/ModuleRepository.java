package com.github.kinetic.logthing.module;

import java.util.*;

@SuppressWarnings("unused")
public class ModuleRepository {
    private static final ModuleRepository INSTANCE = new ModuleRepository();
    private final Map<String, Module> modules = new HashMap<>();

    public ModuleRepository() {
    }

    public static ModuleRepository getInstance() {
        return INSTANCE;
    }

    public final void registerModule(final Module module) {
        modules.put(module.getName().toLowerCase(), module);
    }

    public final <T extends Module> T getModule(final Class<T> moduleClass) {
        return modules.values()
                .stream()
                .filter(module -> moduleClass.equals(module.getClass()))
                .map(moduleClass::cast)
                .findFirst()
                .orElse(null);
    }

    public final Module getModuleByName(final String name) {
        return modules.get(name.toLowerCase());
    }

    public final Collection<Module> getModules() {
        return Collections.unmodifiableCollection(modules.values());
    }

    public final List<Module> getEnabledModules() {
        return modules.values().stream()
                .filter(Module::isEnabled)
                .toList();
    }
}
