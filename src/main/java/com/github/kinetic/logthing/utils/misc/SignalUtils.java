package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;
import sun.misc.Signal;

public final class SignalUtils implements Utils {

    public void setupHandlers() {
        try {
            Signal.handle(new Signal("INT"), _ -> {
                log.info("Received interrupt signal, shutting down gracefully...");
                System.exit(0);
            });

            Signal.handle(new Signal("TERM"), _ -> {
                log.info("Received termination signal, shutting down gracefully...");
                System.exit(0);
            });
        } catch(IllegalArgumentException ex) {
            log.warn("Signal handling not supported on this platform: " + ex.getMessage());
        }
    }
}
