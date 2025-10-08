package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.util.Util;

import java.util.Arrays;

public class StdUtil implements Util {

    public <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }
}
