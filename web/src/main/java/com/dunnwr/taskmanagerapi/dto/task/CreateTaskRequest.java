package com.dunnwr.taskmanagerapi.dto.task;

import java.time.LocalDateTime;

public record CreateTaskRequest(
    String title,
    String description,
    String priority,
    LocalDateTime dueDate,
    Long userId
) {}
