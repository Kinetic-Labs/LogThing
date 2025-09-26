package com.github.kinetic.logthing.module;

import java.util.*;

@SuppressWarnings({"unchecked", "unused"})
public class ModuleRepository {
    private static final ModuleRepository INSTANCE = new ModuleRepository();
    private final Map<String, Module> modules = new HashMap<>();

    public ModuleRepository() {
    }

    public static ModuleRepository getInstance() {
        return INSTANCE;
    }

    public void registerModule(Module module) {
        modules.put(module.getName().toLowerCase(), module);
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        return (T) modules.values()
                .stream()
                .filter(module -> module.getClass().equals(moduleClass))
                .findFirst()
                .orElse(null);
    }

    public Module getModuleByName(String name) {
        return modules.get(name.toLowerCase());
    }

    public Collection<Module> getModules() {
        return Collections.unmodifiableCollection(modules.values());
    }

    public List<Module> getEnabledModules() {
        return modules.values().stream()
                .filter(Module::isEnabled)
                .toList();
    }
}
