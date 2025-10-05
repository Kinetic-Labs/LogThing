package com.github.kinetic.logthing.module;

import com.github.kinetic.logthing.utils.io.log.LogUtils;

public final class ModuleBuilder {
    private final ModuleRepository repository;
    private final LogUtils log = new LogUtils();

    private ModuleBuilder() {
        this.repository = ModuleRepository.getInstance();
    }

    public static ModuleBuilder create() {
        return new ModuleBuilder();
    }

    public void putAll(final Module... modules) {
        for(Module module : modules) {
            log.info("      > Loading module: " + module.getClass().getSimpleName());
            repository.registerModule(module);
        }
    }
}
