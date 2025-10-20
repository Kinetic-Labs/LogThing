package com.github.kinetic.logthing.config.dsl;

public final class AlertsConfig {

    private final ErrorSpikeConfig errorSpike;

    public AlertsConfig(ErrorSpikeConfig errorSpike) {
        this.errorSpike = errorSpike;
    }

    public ErrorSpikeConfig getErrorSpike() {
        return errorSpike;
    }
}
