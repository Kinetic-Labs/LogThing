package com.github.kinetic.logthing.config.dsl;

/**
 * The inputs config, created by the DSL.
 */
public final class InputsConfig {

    private final FileConfig file;

    public InputsConfig(FileConfig file) {
        this.file = file;
    }

    public FileConfig getFile() {
        return file;
    }
}
