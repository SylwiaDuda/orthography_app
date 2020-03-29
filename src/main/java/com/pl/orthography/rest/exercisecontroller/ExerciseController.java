package com.pl.orthography.rest.exercisecontroller;

import com.pl.orthography.config.security.authorization.InvalidJwtAuthenticationException;
import com.pl.orthography.config.security.authorization.JwtTokenProvider;
import com.pl.orthography.rest.usercontroller.jsontemplates.BasicRequestForm;
import com.pl.orthography.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/exercise")
public class ExerciseController {

    private JwtTokenProvider jwtTokenProvider;

    private ExerciseService exerciseService;


    @Autowired
    public ExerciseController(ExerciseService exerciseService, JwtTokenProvider jwtTokenProvider) {
        this.exerciseService = exerciseService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ResponseBody
    @RequestMapping(value = "gettypes", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity getExercise(@RequestBody BasicRequestForm basicRequestForm) {

        try {
            jwtTokenProvider.validateToken(basicRequestForm.getToken());
            return ResponseEntity.status(HttpStatus.OK).body(exerciseService.findExerciseTypes());
        } catch (InvalidJwtAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}