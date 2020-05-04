package com.pl.orthography.rest.testontroller;

import com.pl.orthography.config.security.authorization.InvalidJwtAuthenticationException;
import com.pl.orthography.config.security.authorization.JwtTokenProvider;
import com.pl.orthography.exception.TestException;
import com.pl.orthography.exception.UserException;
import com.pl.orthography.rest.BasicRequestForm;
import com.pl.orthography.rest.testontroller.jsontemplates.TestResultForm;
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
    public ResponseEntity getTest(@RequestBody BasicRequestForm basicRequestForm) {

        try {
            String token = basicRequestForm.getToken();
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            return ResponseEntity.status(HttpStatus.OK).body(userTestService.findUserTests(email));
        } catch (InvalidJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ResponseBody
    @RequestMapping(value = "saveresult", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity saveResult(@RequestBody TestResultForm testResultForm) {

        try {
            String token = testResultForm.getToken();
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            userTestService.saveResult(email, testResultForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (InvalidJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (TestException | UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}