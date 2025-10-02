package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

import java.math.BigInteger;

public class HashUtils extends Utils {
    public HashUtils() {
        super("hash_utils");
    }

    public String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }
}
