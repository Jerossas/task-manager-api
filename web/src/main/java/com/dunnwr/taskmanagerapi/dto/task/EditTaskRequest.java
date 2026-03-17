package com.dunnwr.taskmanagerapi.dto.task;

import java.time.LocalDateTime;

public record EditTaskRequest(
        String title,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate
) {}
