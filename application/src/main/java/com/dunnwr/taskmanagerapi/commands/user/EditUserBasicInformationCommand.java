package com.dunnwr.taskmanagerapi.commands.user;

public record EditUserBasicInformationCommand(
    String firstName,
    String middleName,
    String lastName,
    String gender,
    String email
) {}
