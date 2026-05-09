package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatusTest {

    @Test
    void creatingStatusWithNullValueShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Status.from(null));
        assertEquals("Status cannot be null.", ex.getMessage());
    }

    @Test
    void creatingStatusWithInvalidOptionShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Status.from("invalid"));
        assertEquals("Invalid status value: invalid", ex.getMessage());
    }

    @Test
    void whenValidStringNewStatusShouldReturnNewStatus() {
        Status status = Status.from("new");
        assertEquals("NEW", status.name());
    }

    @Test
    void whenValidStringInProgressStatusShouldReturnInProgressStatus() {
        Status status = Status.from("in_progress");
        assertEquals("IN_PROGRESS", status.name());
    }

    @Test
    void whenValidStringDoneStatusShouldReturnDoneStatus() {
        Status status = Status.from("done");
        assertEquals("DONE", status.name());
    }
}
