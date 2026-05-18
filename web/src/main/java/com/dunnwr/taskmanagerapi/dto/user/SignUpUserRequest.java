package com.dunnwr.taskmanagerapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpUserRequest(
        String firstName,
        String middleName,
        String lastName,
        String password,
        String confirmPassword,
        String email,
        @Schema(
                description = "Genero de la persona",
                allowableValues = {"MALE", "FEMALE"},
                example = "MALE"
        )
        String gender
) {
}
