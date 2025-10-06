package com.github.kinetic.logthing.web;

import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.web.impl.LogsHandler;
import com.github.kinetic.logthing.web.impl.ProcessHandler;
import com.github.kinetic.logthing.web.impl.RootHandler;
import com.github.kinetic.logthing.web.impl.WebHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class Server {
    private final short port;
    private final LogUtils log;
    private HttpServer server;

    public Server(short port) {
        this.log = new LogUtils();
        this.port = port;
    }

    public void start() {
        log.info("Creating server on...");

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch(final IOException ioException) {
            log.trace("Error creating server", ioException);
            System.exit(-1);
        }

        server.createContext("/", new RootHandler());
        server.createContext("/web", new WebHandler());
        server.createContext("/api/process", new ProcessHandler());
        server.createContext("/api/load", new LogsHandler());
        server.setExecutor(null);

        log.info("Server started on :" + port);
        server.start();
    }

    public void stop() {
        if(server != null)
            server.stop(0);
    }
}
