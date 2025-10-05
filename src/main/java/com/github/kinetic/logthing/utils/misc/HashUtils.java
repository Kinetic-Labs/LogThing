package com.github.kinetic.logthing.utils.misc;

import com.github.kinetic.logthing.utils.Utils;

import java.math.BigInteger;

public final class HashUtils implements Utils {

    public String convertToHex(final byte[] messageDigest) {
        final BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);

        while(hexText.length() < 32)
            hexText = "0".concat(hexText);

        return hexText;
    }
}
