package com.dunnwr.taskmanagerapi.controllers;

import com.dunnwr.taskmanagerapi.commands.user.SignInUserCommand;
import com.dunnwr.taskmanagerapi.commands.user.SignUpUserCommand;
import com.dunnwr.taskmanagerapi.dto.user.SignInUserRequest;
import com.dunnwr.taskmanagerapi.dto.user.SignUpUserRequest;
import com.dunnwr.taskmanagerapi.dto.user.TokenResponse;
import com.dunnwr.taskmanagerapi.dto.user.UserResponse;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.services.JwtService;
import com.dunnwr.taskmanagerapi.usecases.user.SignInUserUseCase;
import com.dunnwr.taskmanagerapi.usecases.user.SignUpUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final SignUpUserUseCase signUpUserUseCase;
    private final SignInUserUseCase signInUserUseCase;
    private final JwtService jwtService;

    public AuthController(SignUpUserUseCase signUserInUseCase, SignInUserUseCase signInUserUseCase, JwtService service){
        this.signUpUserUseCase = signUserInUseCase;
        this.signInUserUseCase = signInUserUseCase;
        this.jwtService = service;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> signUpUser(@RequestBody SignUpUserRequest request){

        SignUpUserCommand command = new SignUpUserCommand(
                request.firstName(),
                request.middleName(),
                request.lastName(),
                request.password(),
                request.confirmPassword(),
                request.email(),
                request.gender()
        );

        User createdUser = signUpUserUseCase.execute(command);

        UserResponse response = new UserResponse(
                createdUser.getFirstName(),
                createdUser.getMiddleName(),
                createdUser.getLastName(),
                createdUser.getEmail().getValue(),
                createdUser.getGender().name(),
                createdUser.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> singInUser(@RequestBody SignInUserRequest request){

        SignInUserCommand command = new SignInUserCommand(
                request.email(),
                request.password()
        );

        User loggedUser = signInUserUseCase.execute(command);

        TokenResponse token = new TokenResponse(jwtService.generate(loggedUser));

        return ResponseEntity.status(HttpStatus.OK). body(token);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOutUser(){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
