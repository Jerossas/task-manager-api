package com.dunnwr.taskmanagerapi.dto.user;

public record SignInUserRequest(
   String email,
   String password
) {}
