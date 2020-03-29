package com.pl.orthography.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class BasicRequestForm implements Serializable {
    private String token;
}