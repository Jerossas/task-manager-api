package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @Test
    void whenNullPasswordShouldThrowInvalidFieldException() {
        InvalidFieldException exception = assertThrows(InvalidFieldException.class, () -> Password.validate(null));
        assertEquals("Password cannot be null.", exception.getMessage());
    }

    @Test
    void whenEmptyPasswordShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Password.validate(""));
        assertEquals("Password cannot be empty.", ex.getMessage());
    }

    @Test
    void whenPasswordIsLessThan8CharactersShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Password.validate("1234567"));
        assertEquals("Password must be at least 8 characters long.", ex.getMessage());
    }

    @Test
    void passwordWithNoUppercaseLetterShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Password.validate("12345678"));
        assertEquals("Password must contain at least one uppercase letter.", ex.getMessage());
    }

    @Test
    void passwordWithNoLowercaseLetterShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Password.validate("A2345678"));
        assertEquals("Password must contain at least one lowercase letter.", ex.getMessage());
    }

    @Test
    void passwordWithNoNumberShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Password.validate("Abcdefgh"));
        assertEquals("Password must contain at least one number.", ex.getMessage());
    }

    @Test
    void passwordWithNoSpecialCharacterShouldThrowInvalidFielException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Password.validate("Ab345678"));
        assertEquals("Password must contain at least one special character (@$!%*?&).", ex.getMessage());
    }

    @Test
    void whenValidPasswordShouldNotThrow() {
        assertDoesNotThrow(() -> Password.validate("Ab34567@"));
    }

    @Test
    void whenEncodedPasswordShouldReturnValue() {
        Password password = Password.fromEncoded("encoded-hash");
        assertEquals("encoded-hash", password.getValue());
    }

}
