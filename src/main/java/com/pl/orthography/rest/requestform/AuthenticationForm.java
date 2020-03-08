package com.pl.orthography.rest.requestform;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AuthenticationForm implements Serializable {
    private String email;
    private String password;
}
