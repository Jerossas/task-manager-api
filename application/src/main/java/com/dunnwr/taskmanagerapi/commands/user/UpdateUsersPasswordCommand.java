package com.dunnwr.taskmanagerapi.commands.user;

public record UpdateUsersPasswordCommand(
        String currentPassword,
        String newPassword,
        String newPasswordConfirmation,
        String email
) {}
