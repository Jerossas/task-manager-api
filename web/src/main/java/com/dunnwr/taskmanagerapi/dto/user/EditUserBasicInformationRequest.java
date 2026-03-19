package com.dunnwr.taskmanagerapi.dto.user;

public record EditUserBasicInformationRequest(
        String firstName,
        String middleName,
        String lastName,
        String gender
) {}
