package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.util.Util;

import java.util.Arrays;

/**
 * Utilities for working with standard types
 */
public class StdUtil implements Util {

    /**
     * Concatenate two arrays
     *
     * @param first  the first array to concatenate
     * @param second the second array to concatenate
     * @param <T>    any type
     * @return the concatenated array
     */
    public <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }
}
