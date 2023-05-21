package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    private static final int SALT_LENGTH = 16;
    private static final String SHA_256_ALGORITHM = "SHA-256";

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    @Override
    public String encode(CharSequence password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
            messageDigest.update(salt);
            messageDigest.update(password.toString().getBytes());
            byte[] hashedBytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : salt) {
                sb.append(String.format("%02x", b));
            }
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String hashedPassword) {
        try {
            byte[] hashedPasswordBytes = hexStringToByteArray(hashedPassword);
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(hashedPasswordBytes, 0, salt, 0, SALT_LENGTH);
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
            messageDigest.update(salt);
            messageDigest.update(rawPassword.toString().getBytes());
            byte[] hashedBytes = messageDigest.digest();
            for (int i = 0; i < hashedBytes.length; i++) {
                if (hashedBytes[i] != hashedPasswordBytes[SALT_LENGTH + i]) {
                    return false;
                }
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
