package com.pl.orthography.rest.synchronizationcontroller;

import com.pl.orthography.rest.BasicRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class SynchronizationForm extends BasicRequestForm {
    private LocalDateTime date;
}
