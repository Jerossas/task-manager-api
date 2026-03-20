package com.dunnwr.taskmanagerapi.controllers;

import com.dunnwr.taskmanagerapi.commands.user.DeleteUserCommand;
import com.dunnwr.taskmanagerapi.commands.user.EditUserBasicInformationCommand;
import com.dunnwr.taskmanagerapi.commands.user.GetUserProfileCommand;
import com.dunnwr.taskmanagerapi.commands.user.UpdateUsersPasswordCommand;
import com.dunnwr.taskmanagerapi.dto.user.*;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.services.JwtService;
import com.dunnwr.taskmanagerapi.usecases.user.DeleteUserUseCase;
import com.dunnwr.taskmanagerapi.usecases.user.EditUserBasicInformationUseCase;
import com.dunnwr.taskmanagerapi.usecases.user.GetUserProfileUseCase;
import com.dunnwr.taskmanagerapi.usecases.user.UpdateUsersPasswordUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final EditUserBasicInformationUseCase editUserBasicInformationUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UpdateUsersPasswordUseCase updateUsersPasswordUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final JwtService jwtService;

    public UserController(
            EditUserBasicInformationUseCase editUserBasicInformationUseCase,
            GetUserProfileUseCase getUserProfileUseCase,
            UpdateUsersPasswordUseCase updateUsersPasswordUseCase,
            DeleteUserUseCase deleteUserUseCase,
            JwtService jwtService
    ) {
        this.editUserBasicInformationUseCase = editUserBasicInformationUseCase;
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.updateUsersPasswordUseCase = updateUsersPasswordUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.jwtService = jwtService;
    }

    @PatchMapping("/edit-info")
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

    @GetMapping("/my-profile")
    public ResponseEntity<UserResponse> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {

        GetUserProfileCommand command = new GetUserProfileCommand(userDetails.getUsername());

        User user = getUserProfileUseCase.execute(command);

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

    @PatchMapping("/update-password")
    public ResponseEntity<TokenResponse> updatePassword(@RequestBody UpdatePasswordRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        UpdateUsersPasswordCommand command = new UpdateUsersPasswordCommand(
                request.currentPassword(),
                request.newPassword(),
                request.newPasswordConfirmation(),
                userDetails.getUsername()
        );

        User user = updateUsersPasswordUseCase.execute(command);

        TokenResponse token = new TokenResponse(jwtService.generate(user));

        return ResponseEntity.status(HttpStatus.OK). body(token);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequest request, @AuthenticationPrincipal UserDetails userDetails){

        DeleteUserCommand command = new DeleteUserCommand(
                request.currentPassword(),
                userDetails.getUsername()
        );

        deleteUserUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
