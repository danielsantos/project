package com.aplinotech.cadastrocliente.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Random;

public class Util {

    private static Random getTheBestSecureRandomAvailable() {
        try {
            return SecureRandom.getInstance("IBMSecureRandom", "IBMJCECCA");
        } catch (GeneralSecurityException e) {
            return new SecureRandom();
        }
    }

    public static String geraToken() {
        Random random = getTheBestSecureRandomAvailable();
        ByteBuffer buffer = ByteBuffer.allocate((22 * 6 + Byte.SIZE - 1) / Byte.SIZE);
        random.nextBytes(buffer.array());
        final String token = Base64.encodeBase64URLSafeString(buffer.array());
        return truncateString(token, 22);
    }

    private static String truncateString(String str, int maximumLength) {
        return (str.length() <= maximumLength) ? str : str.substring(0, maximumLength);
    }

}
