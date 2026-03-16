package com.dunnwr.taskmanagerapi.commands.user;

public record SignUpUserCommand(
        String firstName,
        String middleName,
        String lastName,
        String password,
        String confirmPassword,
        String email,
        String gender
) {
}
