package com.github.kinetic.logthing.util.web;

import com.github.kinetic.logthing.util.Util;

/**
 * Constants for the web server
 */
public final class WebConstants implements Util {

    /**
     * Web root directory
     */
    public static final String WEB_ROOT = "web";

    /**
     * Web server port
     */
    public static final long PORT = configUtil.getWebConfig().port();

}
