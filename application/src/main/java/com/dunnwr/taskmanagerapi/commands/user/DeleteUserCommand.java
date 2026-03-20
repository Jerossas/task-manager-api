package com.dunnwr.taskmanagerapi.commands.user;

public record DeleteUserCommand(
        String currentPassword,
        String email
) {}
