package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoleTest {

    @Test
    void creatingRoleWithNullValueShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Role.from(null));
        assertEquals("Role cannot be null.", ex.getMessage());
    }

    @Test
    void creatingRoleWithAnInvalidOptionShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> Role.from("SUPER_ADMIN"));
        assertEquals("Invalid role value: SUPER_ADMIN", ex.getMessage());
    }

    @Test
    void whenValidStringAdminRoleShouldReturnAdminRole() {
        Role role = Role.from("admin");
        assertEquals("ADMIN", role.name());
    }

    @Test
    void whenValidStringUserRoleShouldReturnUserRole() {
        Role role = Role.from("user");
        assertEquals("USER", role.name());
    }
}
