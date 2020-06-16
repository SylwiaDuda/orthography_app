package com.pl.orthography.rest.testontroller.jsontemplates;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class TestResultDto {
    private Long testId;
    private LocalDateTime date;
    private int gainedPoints;
}