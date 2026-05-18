package com.dunnwr.taskmanagerapi.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CreateTaskRequest(
    String title,
    String description,
    @Schema(
            description = "Prioridad de la tarea",
            allowableValues = {"LOW", "MEDIUM", "HIGH"},
            example = "HIGH"
    )
    String priority,
    LocalDateTime dueDate
) {}
