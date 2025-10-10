package com.github.kinetic.logthing;

import com.github.kinetic.logthing.util.io.log.LogUtil;
import com.github.kinetic.logthing.util.misc.StdUtil;

public class Start {
    private static final LogUtil log = new LogUtil();
    private static final StdUtil stdUtil = new StdUtil();

    public static void main(String[] args) {
        Thread.currentThread().setName("B");

        log.info("Booting...");

        LogThing.getInstance().launch(stdUtil.concat(new String[]{""}, args));
    }
}
