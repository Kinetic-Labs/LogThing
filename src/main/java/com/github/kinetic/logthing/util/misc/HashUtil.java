package com.github.kinetic.logthing.util.misc;

import com.github.kinetic.logthing.util.Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtil implements Util {

    /**
     * Convert string to hex
     *
     * @param string the string to be converted
     * @return the converted string
     */
    public String convertToHex(final byte[] string) {
        final BigInteger bigint = new BigInteger(1, string);
        final String hexText = bigint.toString(16);

        return String.format("%32s", hexText).replace(' ', '0');
    }

    /**
     * Convert {@link String} into MD5 hash
     *
     * <p>
     * Note: this can return null if the algorithm decides to not exist?
     * </p>
     *
     * @param string string to be hashed
     * @return result of hashing string
     */
    public String toMD5(final String string) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] messageDigest = md.digest(string.getBytes());

            return convertToHex(messageDigest);
        } catch(final NoSuchAlgorithmException noSuchAlgorithmException) {
            log.trace("Failed to hash MD5", noSuchAlgorithmException);

            return null;
        }
    }
}
