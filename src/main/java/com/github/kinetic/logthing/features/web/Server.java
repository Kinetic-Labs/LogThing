package com.github.kinetic.logthing.features.web;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.features.web.impl.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class Server {
    private final short port;
    private final LogUtil log;
    private HttpServer server;

    /**
     * Create a {@link Server} object
     *
     * @param port the port to serve on
     */
    public Server(long port) {
        this.log = new LogUtil();
        this.port = (short) port;
    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch(final IOException ioException) {
            log.trace("Error creating server", ioException);
            System.exit(-1);
        }

        server.createContext("/", new RootHandler());
        server.createContext("/dashboard", new DashboardHandler());
        server.createContext("/web", new WebHandler());
        server.createContext("/api/process", new ProcessHandler());
        server.createContext("/api/load", new LogsHandler());
        server.createContext("/api/logkinds", new LogKindHandler());
        server.setExecutor(null);

        server.start();
        log.info("Server started on :" + port);
    }

    public void stop() {
        if(server != null)
            server.stop(0);
    }
}
