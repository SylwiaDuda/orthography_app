package com.pl.orthography.data.dto;

import java.time.LocalDateTime;

public interface UserTestDto {

    Long getTestId();

    int getPoints();

    LocalDateTime getDate();
}
