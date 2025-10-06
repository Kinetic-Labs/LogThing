package com.github.kinetic.logthing;

import com.github.kinetic.logthing.utils.io.log.LogUtils;

import java.util.Arrays;

public class Start {
    private static final LogUtils log = new LogUtils();

    static void main(String[] args) {
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
