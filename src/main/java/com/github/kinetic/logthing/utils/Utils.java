package com.github.kinetic.logthing.utils;

import com.github.kinetic.logthing.utils.io.log.LogUtils;
import com.github.kinetic.logthing.utils.io.log.Logger;

public class Utils {
    protected final LogUtils log = Logger.getInstance();
    private final String name;

    public Utils(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
