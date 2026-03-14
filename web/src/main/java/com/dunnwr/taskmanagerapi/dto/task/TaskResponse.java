package com.dunnwr.taskmanagerapi.dto.task;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        LocalDateTime dueDate
) {}
