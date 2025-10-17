package com.github.kinetic.logthing.util;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.misc.ConfigUtil;
import com.github.kinetic.logthing.util.misc.StringUtil;

/**
 * Exposes utilities commonly used in other utilities
 */
public interface Util {
    /**
     * Log util
     */
    LogUtil log = new LogUtil();

    /**
     * String util
     */
    StringUtil stringUtil = new StringUtil();

    /**
     * Config util
     */
    ConfigUtil configUtil = new ConfigUtil();
}
