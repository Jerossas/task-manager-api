package com.dunnwr.taskmanagerapi.commands.task;

public record DeleteUsersTaskCommand(
        Long taskId,
        String email
) {
}
