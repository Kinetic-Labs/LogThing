package com.github.kinetic.logthing.config.dsl;

public record DatabaseConfig(
        String database,
        String username,
        String password,
        String host,
        String uri,
        int port
) {
}
