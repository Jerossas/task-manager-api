package com.dunnwr.taskmanagerapi.commands.task;

import com.dunnwr.taskmanagerapi.models.task.Priority;

import java.time.LocalDateTime;

public record CreateTaskCommand(
    String title,
    String description,
    String priority,
    LocalDateTime dueDate,
    String email
) {}
