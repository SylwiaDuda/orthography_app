package com.pl.orthography.rest.usercontroller.jsontemplates;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class BasicRequestForm implements Serializable {

    private String email;
    private String token;
}
