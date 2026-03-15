package com.dunnwr.taskmanagerapi.commands.user;

import com.dunnwr.taskmanagerapi.models.user.Gender;

public record SignUserInCommand(
        String firstName,
        String middleName,
        String lastName,
        String password,
        String email,
        String gender
) {
}
