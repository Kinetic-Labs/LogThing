package com.github.kinetic.logthing.config.dsl;

public final class WebConfig {

    private final int port;

    public WebConfig(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
