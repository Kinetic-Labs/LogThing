package com.github.kinetic.logthing;

import com.github.kinetic.logthing.module.ModuleBuilder;
import com.github.kinetic.logthing.module.ModuleRepository;
import com.github.kinetic.logthing.module.impl.web.WebMonitorModule;
import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.misc.Terminal;
import sun.misc.Signal;

import java.io.IOException;

@SuppressWarnings("unused")
public class Main {
    private static final LogUtils log = new LogUtils();
    private static final Terminal terminal = new Terminal();

    private static void initializeModules() {
        ModuleBuilder.create().putAll(
                new WebMonitorModule()
        );
    }

    private static void destroyModules() {
        ModuleRepository.getInstance().getEnabledModules().forEach(module -> {
            log.info("Removing module: " + module.getName());

            if(module.isEnabled())
                module.toggle();
        });
    }

    private static void disableControlEcho() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if(!os.contains("win") && !terminal.isIntellijDebug()) {
                new ProcessBuilder("stty", "-echoctl")
                        .inheritIO()
                        .start()
                        .waitFor();
            }
        } catch(IOException | InterruptedException _) {
        }
    }

    private static void enableControlEcho() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if(!os.contains("win") && !terminal.isIntellijDebug()) {
                new ProcessBuilder("stty", "echoctl")
                        .inheritIO()
                        .start()
                        .waitFor();
            }
        } catch(IOException | InterruptedException _) {
        }
    }

    private static void setupSignalHandlers() {
        try {
            Signal.handle(new Signal("INT"), signal -> {
                log.info("Received interrupt signal, shutting down gracefully...");
                System.exit(0);
            });

            Signal.handle(new Signal("TERM"), signal -> {
                log.info("Received termination signal, shutting down gracefully...");
                System.exit(0);
            });
        } catch(IllegalArgumentException ex) {
            log.warn("Signal handling not supported on this platform: " + ex.getMessage());
        }
    }

    private static void initialize() {
        log.info("Initializing LogThing...");

        log.info("Disabling control echo");
        disableControlEcho();

        log.info("Setting up signals...");
        setupSignalHandlers();

        log.info("Initializing Modules...");
        initializeModules();

        log.info("Initialized LogThing.");
    }

    private static void destroy() {
        Thread.currentThread().setName("MainShutdownHook");
        log.info("Shutting down LogThing...");

        log.info("Unloading Modules...");
        destroyModules();

        log.info("Re-enabling control echo...");
        enableControlEcho();

        log.info("Shut down LogThing.");
    }

    static void main(String[] args) {
        Thread.currentThread().setName("Main");

        if(terminal.isIntellijDebug()) log.debug("terminal.isDebug()");

        initialize();

        Runtime.getRuntime().addShutdownHook(new Thread(Main::destroy));

        try {
            Thread.currentThread().join();
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("Main thread interrupted");
        }
    }
}
