package com.pl.orthography.rest.testontroller.jsontemplates;

import com.pl.orthography.rest.BasicRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TestResultsForm extends BasicRequestForm {
    private List<TestResultDto> testResults;
}