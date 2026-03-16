package com.dunnwr.taskmanagerapi.controller;

import com.dunnwr.taskmanagerapi.commands.user.SignUpUserCommand;
import com.dunnwr.taskmanagerapi.dto.user.SignUserInRequest;
import com.dunnwr.taskmanagerapi.dto.user.UserResponse;
import com.dunnwr.taskmanagerapi.models.user.User;
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

    public AuthController(SignUpUserUseCase signUserInUseCase){
        this.signUpUserUseCase = signUserInUseCase;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> signUpUser(@RequestBody SignUserInRequest request){

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
}
