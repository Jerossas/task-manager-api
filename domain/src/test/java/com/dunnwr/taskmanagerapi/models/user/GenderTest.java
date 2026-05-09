package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenderTest {

    @Test
    void creatingGenderWithNullValueShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Gender.from(null));
        assertEquals("Gender cannot be null.", ex.getMessage());
    }

    @Test
    void creatingGenderWithInvalidOptionShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Gender.from("invalid"));
        assertEquals("Invalid gender value: invalid", ex.getMessage());
    }

    @Test
    void whenValidStringMaleGenderShouldReturnMaleGender() {
        Gender gender = Gender.from("male");
        assertEquals("MALE", gender.name());
    }

    @Test
    void whenValidStringFemaleGenderShouldReturnFemaleGender() {
        Gender gender = Gender.from("female");
        assertEquals("FEMALE", gender.name());
    }
}
