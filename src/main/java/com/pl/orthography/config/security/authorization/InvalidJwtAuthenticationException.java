package com.pl.orthography.config.security.authorization;

public class InvalidJwtAuthenticationException extends Exception {

    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}