package com.pl.orthography.rest.synchronizationcontroller;

import com.pl.orthography.config.security.authorization.InvalidJwtAuthenticationException;
import com.pl.orthography.config.security.authorization.JwtTokenProvider;
import com.pl.orthography.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/rest/synchronization")
public class SynchronizationController {

    private JwtTokenProvider jwtTokenProvider;
    private UserTestService userTestService;

    @Autowired
    public SynchronizationController(JwtTokenProvider jwtTokenProvider, UserTestService userTestService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userTestService = userTestService;
    }

    @ResponseBody
    @RequestMapping(value = "synchronize", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity synchronize(@RequestBody SynchronizationForm form) {

        try {
            String token = form.getToken();
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            LocalDateTime lastDateTest = userTestService.findLastDateTest(email);

            if(lastDateTest.isEqual(form.getDate())){
                return ResponseEntity.noContent().build();
            }else if(lastDateTest.isAfter(form.getDate())){
                return ResponseEntity.status(HttpStatus.OK).body(userTestService.findTestAfterDate(email,form.getDate()));
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(lastDateTest);
            }
        } catch (InvalidJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}