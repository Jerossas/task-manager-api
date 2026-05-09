package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User createValidUser() {
        return new User(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                Password.fromEncoded("encoded-password"),
                Email.of("example@gmail.com"),
                Gender.MALE
        );
    }

    @Test
    void creatingUserWithNullFirstNameShouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new User(
                null,
                null,
                null,
                null,
                null,
                null
        ));

        assertEquals("First name cannot be null.", ex.getMessage());
    }

    @Test
    void creatingUserWithBlankFirstNameShouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new User(
                "",
                null,
                null,
                null,
                null,
                null
        ));

        assertEquals("First name cannot be empty.", ex.getMessage());
    }

    @Test
    void creatingUserWithNullLastNameShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new User(
                "Joan",
                null,
                null,
                null,
                null,
                null
        ));
        assertEquals("Last name cannot be null", ex.getMessage());
    }

    @Test
    void creatingUserWithBlankLastNameShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new User(
                "Joan",
                "Ferley",
                "",
                null,
                null,
                null
        ));

        assertEquals("Last name cannot be empty", ex.getMessage());
    }

    @Test
    void creatingUserWithNullPasswordShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new User(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                null,
                null,
                null
        ));

        assertEquals("The password cannot be null", ex.getMessage());
    }

    @Test
    void creatingUserWithNullGenderShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new User(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                Password.fromEncoded("encoded-password"),
                null,
                null
        ));

        assertEquals("Gender cannot be null.", ex.getMessage());
    }

    @Test
    void creatingUserWithNullEmailShouldThrowInvalidFieldException() {
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> new User(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                Password.fromEncoded("encoded-password"),
                null,
                Gender.MALE
        ));

        assertEquals("Email cannot be null", ex.getMessage());
    }

    @Test
    void givenValidArguments_whenCreatingUser_shouldReturnUserInstance() {
        User user = new User(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                Password.fromEncoded("encoded-password"),
                Email.of("example@gmail.com"),
                Gender.MALE
        );

        assertNotNull(user);
    }

    @Test
    void whenANewUserIsCreatedItShouldHaveOnlyUserRole() {
        User user = new User(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                Password.fromEncoded("encoded-password"),
                Email.of("example@gmail.com"),
                Gender.MALE
        );

        assertEquals(1, user.getRoles().size());

        user.getRoles().stream().findFirst().ifPresent(role -> {
            assertEquals(Role.USER, role);
        });
    }

    @Test
    void changeFirstName_withNullName_shouldThrowInvalidFieldException() {
        User user = createValidUser();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.changeFirstName(null));
        assertEquals("First name cannot be null.", ex.getMessage());
    }

    @Test
    void changeFirstName_withBlankName_shouldThrowInvalidFieldException() {
        User user = createValidUser();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.changeFirstName(""));
        assertEquals("First name cannot be empty.", ex.getMessage());
    }

    @Test
    void changeFirstName_withValidName_updatesName() {
        User user = createValidUser();

        user.changeFirstName("Michael");
        assertEquals("Michael", user.getFirstName());
    }

    @Test
    void changeMiddleName_withNoNullValue_updatesMiddleName() {
        User user = createValidUser();

        user.changeMiddleName("Sebastian");
        assertEquals("Sebastian", user.getMiddleName());
    }

    @Test
    void changeMiddleName_withNullValue_updatesMiddleName() {
        User user = createValidUser();
        user.changeMiddleName(null);
        assertNull(user.getMiddleName());
    }

    @Test
    void changeLastName_withNullLastName_shouldThrowInvalidFieldException() {
        User user = createValidUser();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.changeLastName(null));
        assertEquals("Last name cannot be null", ex.getMessage());
    }

    @Test
    void changeLastName_withBlankLastName_shouldThrowInvalidFieldException() {
        User user = createValidUser();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.changeLastName(""));
        assertEquals("Last name cannot be empty", ex.getMessage());
    }

    @Test
    void changeLastName_withValidLastName_updatesLastName() {
        User user = createValidUser();

        user.changeLastName("Lozano Lopez");
        assertEquals("Lozano Lopez", user.getLastName());
    }

    @Test
    void changePassword_withNullPassword_shouldThrowInvalidFieldException() {
        User user = createValidUser();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.changePassword(null));
        assertEquals("The password cannot be null", ex.getMessage());
    }

    @Test
    void changePassword_withValidPassword_shouldUpdatePassword() {
        User user = createValidUser();

        user.changePassword(Password.fromEncoded("new-encoded-password"));
        assertEquals("new-encoded-password", user.getPassword().getValue());
    }

    @Test
    void changeGender_withNullGender_shouldThrowInvalidFieldException() {
        User user = createValidUser();
        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.changeGender(null));
        assertEquals("Gender cannot be null.", ex.getMessage());
    }

    @Test
    void changeGender_withValidGender_shouldUpdateGender() {
        User user = createValidUser();

        user.changeGender(Gender.FEMALE);
        assertEquals("FEMALE", user.getGender().name());
    }

    @Test
    void addRole_withNullRole_shouldReturnInvalidFieldException() {
        User user = createValidUser();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.addRole(null));
        assertEquals("Role cannot be null", ex.getMessage());
    }

    @Test
    void addRole_withValidRole_shouldUpdateUserRoleList() {
        User user = createValidUser();

        assertFalse(user.getRoles().contains(Role.ADMIN));

        user.addRole(Role.ADMIN);

        assertEquals(2, user.getRoles().size());
    }

    @Test
    void removeRole_deleteUserRole_ShouldReturnInvalidFieldException() {
        User user = createValidUser();

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> user.removeRole(Role.USER));
        assertEquals("Cannot remove the USER role.", ex.getMessage());
    }

    @Test
    void removeRole_deleteAnyOtherRoleDifferentFromUser_shouldUpdateUserRoleList() {
        User user = createValidUser();

        user.addRole(Role.ADMIN);
        assertTrue(user.getRoles().contains(Role.ADMIN));
        assertEquals(2, user.getRoles().size());

        user.removeRole(Role.ADMIN);
        assertFalse(user.getRoles().contains(Role.ADMIN));
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void restore_validRecordFromDatabase_shouldReturnUser() {
        User user = User.restore(
                1L,
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                Password.fromEncoded("encoded-hash"),
                Email.fromStored("example@domain.com"),
                Gender.MALE,
                new HashSet<>(Set.of(Role.USER))
        );

        assertNotNull(user);
    }

}
