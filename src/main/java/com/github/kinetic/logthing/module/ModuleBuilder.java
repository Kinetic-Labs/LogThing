package com.github.kinetic.logthing.module;

import com.github.kinetic.logthing.util.io.log.LogUtil;

public final class ModuleBuilder {
    private final ModuleRepository repository;
    private final LogUtil log = new LogUtil();

    private ModuleBuilder() {
        this.repository = ModuleRepository.getInstance();
    }

    public static ModuleBuilder create() {
        return new ModuleBuilder();
    }

    /**
     * Put modules into repository
     *
     * @param modules modules to be put into repository
     */
    public void putAll(final Module... modules) {
        for(Module module : modules) {
            log.debug("      > Loading module: " + module.getClass().getSimpleName());
            repository.registerModule(module);
        }
    }
}
