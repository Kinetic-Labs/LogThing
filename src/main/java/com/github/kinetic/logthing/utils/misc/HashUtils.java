package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

import java.math.BigInteger;

public final class HashUtils implements Utils {

    /**
     * Convert string to hex
     *
     * @param string the string to be converted
     * @return the converted string
     */
    public String convertToHex(final byte[] string) {
        final BigInteger bigint = new BigInteger(1, string);
        String hexText = bigint.toString(16);

        while(hexText.length() < 32)
            hexText = "0".concat(hexText);

        return hexText;
    }
}
