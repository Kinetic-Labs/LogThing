package com.github.kinetic.logthing.config.dsl;

import java.util.List;

public final class FileConfig {

    private final String path;
    private final List<String> logKinds;

    public FileConfig(String path, List<String> logKinds) {
        this.path = path;
        this.logKinds = logKinds;
    }

    public String getPath() {
        return path;
    }

    public List<String> getLogKinds() {
        return logKinds;
    }
}
