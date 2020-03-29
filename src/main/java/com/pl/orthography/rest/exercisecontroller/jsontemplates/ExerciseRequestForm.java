package com.pl.orthography.rest.exercisecontroller.jsontemplates;

import com.pl.orthography.rest.BasicRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ExerciseRequestForm extends BasicRequestForm {
    Long exerciseId;
}
