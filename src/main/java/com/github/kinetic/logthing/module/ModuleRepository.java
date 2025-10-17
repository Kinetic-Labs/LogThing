package com.github.kinetic.logthing.module;

import com.github.kinetic.logthing.util.io.log.LogUtil;

import java.util.*;

/**
 * Repository for modules
 */
@SuppressWarnings("unused")
public final class ModuleRepository {

    /**
     * Singleton instance
     */
    private static final ModuleRepository INSTANCE = new ModuleRepository();

    /**
     * The modules stored in the repository
     */
    private final Map<String, Module> modules = new HashMap<>();

    /**
     * Utilities
     */
    private final LogUtil log = new LogUtil();

    /**
     * Private constructor
     */
    public ModuleRepository() {
    }

    /**
     * @return {@link ModuleRepository} instance
     */
    public static ModuleRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Register a module
     *
     * @param module the {@link Module} to register
     */
    public void registerModule(final Module module) {
        log.debug("ModuleRepository: Registering module: " + module.getName());

        modules.put(module.getName().toLowerCase(), module);
    }

    /**
     * Get a module
     *
     * @param moduleClass the module's class
     * @param <T>         any class that extends {@link Module}
     * @return the module
     */
    public <T extends Module> T getModule(final Class<T> moduleClass) {
        return modules.values()
                .stream()
                .filter(module -> moduleClass.equals(module.getClass()))
                .map(moduleClass::cast)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a module by its name
     *
     * @param name the name of module
     * @return the {@link Module}
     */
    public Module getModuleByName(final String name) {
        return modules.get(name.toLowerCase());
    }

    /**
     * Get all modules
     *
     * @return a {@link Collection} of modules
     */
    public Collection<Module> getModules() {
        return Collections.unmodifiableCollection(modules.values());
    }

    /**
     * Get currently enabled modules
     *
     * @return a {@link List} of enabled modules
     */
    public List<Module> getEnabledModules() {
        return modules.values().stream()
                .filter(Module::isEnabled)
                .toList();
    }
}
