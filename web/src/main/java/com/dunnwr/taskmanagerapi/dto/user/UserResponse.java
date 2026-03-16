package com.dunnwr.taskmanagerapi.dto.user;

import java.util.Set;

public record UserResponse(
        String firstName,
        String middleName,
        String lastName,
        String email,
        String gender,
        Set<String> roles
) {
}
