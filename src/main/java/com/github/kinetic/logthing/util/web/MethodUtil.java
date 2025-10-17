package com.github.kinetic.logthing.util.web;

import com.github.kinetic.logthing.util.Util;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Utilities for working with HTTP methods
 */
public final class MethodUtil implements Util {

    /**
     * WebUtil instance
     */
    private final WebUtil webUtil = new WebUtil();

    /**
     * Require a specific HTTP method
     * @param exchange the {@link HttpExchange}
     * @param methods the methods to require
     */
    public void requireMethod(final HttpExchange exchange, final Method... methods) {
        final String requestMethod = exchange.getRequestMethod();

        for(final Method method : methods) {
            final String methodName = method.name();

            if(methodName.equalsIgnoreCase(requestMethod))
                return;

            try {
                webUtil.send405(exchange);
            } catch(final IOException ioException) {
                log.trace("Failed to send 405", ioException);
            }
        }
    }
}
