package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriorityTest {

    @Test
    void creatingPriorityWithNullValueShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Priority.from(null));
        assertEquals("Priority cannot be null.", ex.getMessage());
    }

    @Test
    void creatingPriorityWithInvalidValueShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Priority.from("invalid"));
        assertEquals("Invalid priority value: invalid", ex.getMessage());
    }

    @Test
    void whenValidStringLowPriorityShouldReturnLowPriority() {
        Priority priority = Priority.from("low");
        assertEquals("LOW", priority.name());
    }

    @Test
    void whenValidStringMediumPriorityShouldReturnMediumPriority() {
        Priority priority = Priority.from("medium");
        assertEquals("MEDIUM", priority.name());
    }

    @Test
    void whenValidStringHighPriorityShouldReturnHighPriority() {
        Priority priority = Priority.from("high");
        assertEquals("HIGH", priority.name());
    }
}
