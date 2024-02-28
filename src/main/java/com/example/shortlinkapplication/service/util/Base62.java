package com.example.shortlinkapplication.service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class Base62 {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int base = ALPHABET.length();

    public String generateShortURL(String originalURL) {
        String jws = Jwts.builder().setSubject(originalURL).signWith(key).compact();
        String hash = jws.substring(0, 10);
        try {
            long buffer = Long.parseLong(hash, 16);
            return encodeToString(buffer);
        } catch (NumberFormatException e) {
            return "Invalid hash format.";
        }

    }
    public String encodeToString(long buffer) {
        if (buffer == 0) {
            return String.valueOf(ALPHABET.charAt(0));
        }

        StringBuilder encodedString = new StringBuilder();

        while (buffer > 0) {
            encodedString.append(ALPHABET.charAt((int) (buffer % base)));
            buffer = buffer / base;
        }
        return encodedString.reverse().toString();
    }

    public long decodeFromString(String input) {
        char[] characters = input.toCharArray();
        var length = characters.length;

        int decoded = 0, counter = 1;

        for (char character : characters) {
            decoded += (int) (ALPHABET.indexOf(character) * Math.pow(base, length - counter));
            counter++;
        }
        return decoded;
    }
}
