package com.github.kinetic.logthing.features.web;

import com.github.kinetic.logthing.features.web.impl.api.logs.LogLevelApiHandler;
import com.github.kinetic.logthing.features.web.impl.api.logs.LogsApiGetHandler;
import com.github.kinetic.logthing.features.web.impl.api.ProcessApiHandler;
import com.github.kinetic.logthing.features.web.impl.api.settings.SettingsApiGetHandler;
import com.github.kinetic.logthing.features.web.impl.api.settings.SettingsApiSetHandler;
import com.github.kinetic.logthing.features.web.impl.files.WebFileHandler;
import com.github.kinetic.logthing.features.web.impl.html.DashboardHtmlHandler;
import com.github.kinetic.logthing.features.web.impl.html.UploadHtmlHandler;
import com.github.kinetic.logthing.features.web.impl.html.SettingsHtmlHandler;
import com.github.kinetic.logthing.util.io.log.LogUtil;
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

        server.createContext("/", new UploadHtmlHandler());
        server.createContext("/dashboard", new DashboardHtmlHandler());
        server.createContext("/web", new WebFileHandler());
        server.createContext("/settings", new SettingsHtmlHandler());
        server.createContext("/api/process", new ProcessApiHandler());
        server.createContext("/api/logs/get", new LogsApiGetHandler());
        server.createContext("/api/logs/levels", new LogLevelApiHandler());
        server.createContext("/api/settings/set", new SettingsApiSetHandler());
        server.createContext("/api/settings/get", new SettingsApiGetHandler());
        server.setExecutor(null);

        server.start();
        log.info("Server started on :" + port);
    }

    public void stop() {
        if(server != null)
            server.stop(0);
    }
}
