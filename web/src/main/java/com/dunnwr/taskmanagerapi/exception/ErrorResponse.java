package com.dunnwr.taskmanagerapi.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String field,
        String message,
        int status,
        LocalDateTime timestamp
) {
}
