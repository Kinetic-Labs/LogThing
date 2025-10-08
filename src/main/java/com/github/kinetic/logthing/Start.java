package com.github.kinetic.logthing;

import com.github.kinetic.logthing.util.io.log.LogUtil;

import java.util.Arrays;

public class Start {
    private static final LogUtil log = new LogUtil();

    public static void main(String[] args) {
        Thread.currentThread().setName("B");

        log.info("Booting...");

        LogThing.getInstance().launch(concat(new String[] {"--debug"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }
}
