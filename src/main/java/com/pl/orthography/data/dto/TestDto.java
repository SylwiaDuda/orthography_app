package com.pl.orthography.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {

    private Long id;
    private Integer requiredPoints;
    private String testType;
    private Integer gainedPoints;

    public TestDto(TestTypesDto testTypesDto) {
        this.id = testTypesDto.getId();
        this.requiredPoints = testTypesDto.getRequiredPoints();
        this.testType = testTypesDto.getTestType();
        this.gainedPoints = 0;
    }
}