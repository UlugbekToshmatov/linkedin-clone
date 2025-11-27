package com.linkedin.linkedin.features.authentication.utils;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.util.Base64;

@UtilityClass
public class Encoder {

    public static String encode(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to encode password: %s", e.getMessage()));
        }
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

}
