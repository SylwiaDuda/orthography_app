package com.pl.orthography.rest.testontroller;

import com.pl.orthography.config.security.authorization.InvalidJwtAuthenticationException;
import com.pl.orthography.config.security.authorization.JwtTokenProvider;
import com.pl.orthography.exception.TestException;
import com.pl.orthography.exception.UserException;
import com.pl.orthography.rest.BasicRequestForm;
import com.pl.orthography.rest.testontroller.jsontemplates.TestRequestForm;
import com.pl.orthography.rest.testontroller.jsontemplates.TestResultDto;
import com.pl.orthography.rest.testontroller.jsontemplates.TestResultsForm;
import com.pl.orthography.service.TestService;
import com.pl.orthography.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/test")
public class TestController {

    private JwtTokenProvider jwtTokenProvider;

    private UserTestService userTestService;
    private TestService testService;

    @Autowired
    public TestController(JwtTokenProvider jwtTokenProvider, UserTestService userTestService, TestService testService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userTestService = userTestService;
        this.testService = testService;
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
    @RequestMapping(value = "getwords", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity getExercise(@RequestBody TestRequestForm form) {

        try {
            jwtTokenProvider.validateToken(form.getToken());

            List<String> words = testService.findETestWords(form.getTestId());

            return words.isEmpty() ?
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build() : ResponseEntity.status(HttpStatus.OK).body(words);

        } catch (InvalidJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ResponseBody
    @RequestMapping(value = "saveresults", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity saveResults(@RequestBody TestResultsForm testResultForm) {

        try {
            String token = testResultForm.getToken();
            jwtTokenProvider.validateToken(token);
            String email = jwtTokenProvider.getUsername(token);

            for (TestResultDto result : testResultForm.getTestResults()) {
                userTestService.saveResult(email, result);
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (InvalidJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (TestException | UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}