package com.dunnwr.taskmanagerapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record UserResponse(
        String firstName,
        String middleName,
        String lastName,
        String email,
        @Schema(
                description = "Genero de la persona",
                allowableValues = {"MALE", "FEMALE"},
                example = "MALE"
        )
        String gender,
        Set<String> roles
) {
}
