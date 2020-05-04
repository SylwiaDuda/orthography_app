package com.pl.orthography.rest.testontroller.jsontemplates;

import com.pl.orthography.rest.BasicRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestResultForm extends BasicRequestForm {
    private Long testId;
    private String date;
    private int gainedPoints;
}
