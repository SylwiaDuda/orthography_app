package com.pl.orthography.rest.usercontroller.jsontemplates;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AuthenticationResponse implements Serializable {
    private long id;
    private String userName;
    private String email;
    private String token;
}