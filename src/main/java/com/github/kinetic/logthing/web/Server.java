package com.github.kinetic.logthing.web;

import com.github.kinetic.logthing.utils.io.LogUtils;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private HttpServer server;
    private final LogUtils log;
    private final short port;

    public Server(short port) {
        log = new LogUtils();
        this.port = port;
    }

    public void start() {
        log.info("Creating server on...");

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch(IOException ex) {
            log.trace("Error creating server", ex);
            System.exit(-1);
        }

        server.createContext("/", new Root());
        server.createContext("/web", new WebHandler());
        server.setExecutor(null);

        log.info("Server started on :" + port);
        server.start();
    }

    public void stop() {
        if(server != null)
            server.stop(0);
    }
}
