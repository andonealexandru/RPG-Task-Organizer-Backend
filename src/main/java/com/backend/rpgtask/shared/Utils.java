package com.backend.rpgtask.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {
    private final Random random = new SecureRandom();
    private final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {

        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; ++i) {
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return new String(returnValue);
    }
}