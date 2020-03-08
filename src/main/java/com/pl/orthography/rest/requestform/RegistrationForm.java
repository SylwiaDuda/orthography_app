package com.pl.orthography.rest.requestform;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationForm {
    private String userName;
    private String  email;
    private String password;
}