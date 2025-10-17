package com.github.kinetic.logthing.module;

import com.github.kinetic.logthing.util.io.log.LogUtil;

/**
 * Builder for modules
 */
public final class ModuleBuilder {

    /**
     * The repository for modules
     */
    private final ModuleRepository repository;

    /**
     * Utilities
     */
    private final LogUtil log = new LogUtil();

    /**
     * Private constructor
     */
    private ModuleBuilder() {
        this.repository = ModuleRepository.getInstance();
    }

    /**
     * Create a new builder
     *
     * @return a new builder
     */
    public static ModuleBuilder create() {
        return new ModuleBuilder();
    }

    /**
     * Put modules into the repository
     *
     * @param modules modules to be put into the repository
     */
    public void putAll(final Module... modules) {
        for(Module module : modules) {
            log.debug("      > Loading module: " + module.getClass().getSimpleName());
            repository.registerModule(module);
        }
    }
}
