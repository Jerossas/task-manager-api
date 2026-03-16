package com.dunnwr.taskmanagerapi.dto.user;

public record SignUserInRequest(
        String firstName,
        String middleName,
        String lastName,
        String password,
        String confirmPassword,
        String email,
        String gender
) {
}
