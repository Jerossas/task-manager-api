package com.dunnwr.taskmanagerapi.commands.task;

import java.time.LocalDateTime;

public record EditUsersTaskCommand(
        Long taskId,
        String title,
        String description,
        String status,
        String priority,
        LocalDateTime dueDate,
        String email
) {}
