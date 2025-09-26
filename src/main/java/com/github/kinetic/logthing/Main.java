package com.github.kinetic.logthing;

import com.github.kinetic.logthing.utils.io.LogUtils;
import com.github.kinetic.logthing.web.Server;

public class Main {
    private static final short port = 9595;
    private static final LogUtils lu = new LogUtils();

    static void main(String[] args) {
        lu.info("Starting LogThing...");
        Server server = new Server(port);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}
