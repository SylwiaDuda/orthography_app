package com.pl.orthography.rest.usercontroller;

import com.pl.orthography.config.security.authorization.JwtTokenProvider;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.rest.usercontroller.jsontemplates.AuthenticationResponse;
import com.pl.orthography.rest.usercontroller.jsontemplates.AuthenticationForm;
import com.pl.orthography.rest.usercontroller.jsontemplates.RegistrationForm;
import com.pl.orthography.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/rest")
public class UserController {

    private UserService userService;
    private final
    AuthenticationManager authenticationManager;
    private final
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ResponseBody
    @RequestMapping(value = "login", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity loginUser(@RequestBody AuthenticationForm userData) {

        User user = userService.findUserByEmailAndPassword(userData.getEmail(), userData.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword()));
            String token = jwtTokenProvider.createToken(userData.getEmail(), Collections.singletonList(user.getRole().name()));

            return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponse(user.getId(), user.getUserName(), user.getEmail(), token));
        }
    }

    @ResponseBody
    @RequestMapping(value = "register", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody RegistrationForm registrationData) {
        if (userService.findUserByEmail(registrationData.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user with the e-mail address already exists");
        }

        User createdUser = userService.createUserAccount(registrationData.getUserName(), registrationData.getEmail(), registrationData.getPassword());
        HttpStatus httpStatus = createdUser != null ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(null);
    }
}