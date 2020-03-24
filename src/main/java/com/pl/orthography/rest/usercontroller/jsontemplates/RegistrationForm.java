package com.pl.orthography.rest.usercontroller.jsontemplates;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationForm {
    private String userName;
    private String email;
    private String password;
}