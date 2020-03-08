package com.pl.orthography.rest.responseform;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
    private String email;
    private String token;
    private String userName;
}