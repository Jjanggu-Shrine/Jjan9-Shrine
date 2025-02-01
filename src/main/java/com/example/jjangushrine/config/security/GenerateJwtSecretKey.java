package com.example.jjangushrine.config.security;

import java.util.Base64;

public class GenerateJwtSecretKey {
    public static void main(String[] args) {
        String originalKey = "jjangushrineprojectsecretkey123!@#";
        String encodedKey = Base64.getEncoder().encodeToString(originalKey.getBytes());
        System.out.println("Generated JWT Secret Key: " + encodedKey);
    }
}
