package com.pl.orthography.rest.testontroller.jsontemplates;

import com.pl.orthography.rest.BasicRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class TestRequestForm extends BasicRequestForm {
    Long testId;
}