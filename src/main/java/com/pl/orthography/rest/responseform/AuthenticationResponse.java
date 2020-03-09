package com.pl.orthography.rest.responseform;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AuthenticationResponse implements Serializable {
    private String user;
    private String email;
    private String token;
}