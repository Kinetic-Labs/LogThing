package com.github.kinetic.logthing.config.dsl;

public final class ErrorSpikeConfig {

    private final String condition;
    private final String window;

    public ErrorSpikeConfig(String condition, String window) {
        this.condition = condition;
        this.window = window;
    }

    public String getCondition() {
        return condition;
    }

    public String getWindow() {
        return window;
    }
}
