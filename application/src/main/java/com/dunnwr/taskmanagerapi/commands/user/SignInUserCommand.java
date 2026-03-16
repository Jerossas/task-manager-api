package com.dunnwr.taskmanagerapi.commands.user;

public record SignInUserCommand(
        String email,
        String password
) {
}
