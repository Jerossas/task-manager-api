package com.dunnwr.taskmanagerapi.dto.user;

public record UpdatePasswordRequest(
   String currentPassword,
   String newPassword,
   String newPasswordConfirmation
) {}
