package com.pl.orthography.rest.testontroller;

import com.pl.orthography.config.security.authorization.InvalidJwtAuthenticationException;
import com.pl.orthography.config.security.authorization.JwtTokenProvider;
import com.pl.orthography.rest.BasicRequestForm;
import com.pl.orthography.service.TestService;
import com.pl.orthography.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/test")
public class TestController {

    private JwtTokenProvider jwtTokenProvider;

    private UserTestService userTestService;

    @Autowired
    public TestController(JwtTokenProvider jwtTokenProvider, UserTestService userTestService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userTestService = userTestService;
    }

    @ResponseBody
    @RequestMapping(value = "get", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity getExercise(@RequestBody BasicRequestForm basicRequestForm) {

        try {
            String token = basicRequestForm.getToken();
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            return ResponseEntity.status(HttpStatus.OK).body(userTestService.findUserTests(email));
        } catch (InvalidJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}