package com.dunnwr.taskmanagerapi.commands.task;

public record FindAUsersTaskCommand(
   String email,
   Long taskId
) {}
