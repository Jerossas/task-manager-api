package com.dunnwr.taskmanagerapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record EditUserBasicInformationRequest(
        String firstName,
        String middleName,
        String lastName,
        @Schema(
                description = "Genero de la persona",
                allowableValues = {"MALE", "FEMALE"},
                example = "MALE"
        )
        String gender
) {}
