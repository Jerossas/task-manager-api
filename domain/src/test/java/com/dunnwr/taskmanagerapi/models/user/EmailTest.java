package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {

    @Test
    void whenNullEmailShouldReturnInvalidFieldException() {
        assertThrows(InvalidFieldException.class, () -> Email.of(null), "Email cannot be null.");
    }

    @Test
    void whenEmptyEmailShouldThrowInvalidFieldException() {
        assertThrows(InvalidFieldException.class, () -> Email.of(""), "Email cannot be empty.");
    }

    @Test
    void whenEmailWithoutAtSignShouldThrowInvalidFieldException() {
        assertThrows(InvalidFieldException.class, () -> Email.of("example_gmail.com"), "Email must contain '@'.");
    }

    @Test
    void emailWithoutLocalPartBeforeAtSignShouldThrowInvalidFieldException() {
        assertThrows(InvalidFieldException.class, () -> Email.of("@gmail.com"), "Email must have a valid local part before '@'.");
    }

    @Test
    void whenEmailWithoutDotInDomainShouldThrowInvalidFieldException() {
        assertThrows(InvalidFieldException.class, () -> Email.of("example@gmail"), "Email domain must contain a '.'.");
    }

    @Test
    void whenEmailWithInvalidDomainExtensionShouldThrowInvalidFieldException() {
        assertThrows(InvalidFieldException.class, () -> Email.of("example@gmail.c"), "Email must have a valid domain extension (e.g. .com, .co).");
    }

    @Test
    void whenValidEmailShouldReturnEmail(){
        Email email = Email.of("example@gmail.com");
        assertEquals("example@gmail.com", email.getValue());
    }

}
