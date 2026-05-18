package com.dunnwr.taskmanagerapi.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        @Schema(
                description = "Estado de la tarea",
                allowableValues = {"NEW", "IN_PROGRESS", "DONE"},
                example = "NEW"
        )
        String status,
        @Schema(
                description = "Prioridad de la tarea",
                allowableValues = {"LOW", "MEDIUM", "HIGH"},
                example = "HIGH"
        )
        String priority,
        LocalDateTime dueDate
) {}
