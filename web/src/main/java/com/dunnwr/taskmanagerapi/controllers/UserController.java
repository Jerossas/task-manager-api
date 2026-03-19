package com.dunnwr.taskmanagerapi.controllers;

import com.dunnwr.taskmanagerapi.commands.user.EditUserBasicInformationCommand;
import com.dunnwr.taskmanagerapi.dto.user.EditUserBasicInformationRequest;
import com.dunnwr.taskmanagerapi.dto.user.UserResponse;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.usecases.user.EditUserBasicInformationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final EditUserBasicInformationUseCase editUserBasicInformationUseCase;

    public UserController(EditUserBasicInformationUseCase editUserBasicInformationUseCase) {
        this.editUserBasicInformationUseCase = editUserBasicInformationUseCase;
    }

    @PatchMapping
    public ResponseEntity<UserResponse> editUserBasicInformation(@RequestBody EditUserBasicInformationRequest request, @AuthenticationPrincipal UserDetails userDetails){

        EditUserBasicInformationCommand command = new EditUserBasicInformationCommand(
                request.firstName(),
                request.middleName(),
                request.lastName(),
                request.gender(),
                userDetails.getUsername()
        );

        User user = editUserBasicInformationUseCase.execute(command);

        UserResponse response = new UserResponse(
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail().getValue(),
                user.getGender().name(),
                user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet())
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
