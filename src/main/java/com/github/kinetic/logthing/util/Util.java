package com.github.kinetic.logthing.util;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.misc.StringUtil;

/**
 * Exposes utilities commonly used in other utilities
 */
public interface Util {
    LogUtil log = new LogUtil();
    StringUtil stringUtil = new StringUtil();
}
