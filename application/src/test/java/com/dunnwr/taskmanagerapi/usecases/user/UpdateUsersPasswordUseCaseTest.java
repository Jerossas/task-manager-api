package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.UpdateUsersPasswordCommand;
import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.user.*;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUsersPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UpdateUsersPasswordUseCaseImpl updateUsersPasswordUseCase;

    private User createValidUser() {
        return User.restore(
                1L,
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                Password.fromEncoded("encoded-hash"),
                Email.fromStored("example@domain.com"),
                Gender.MALE,
                new HashSet<>(Set.of(Role.USER))
        );
    }

    @Test
    void whenNewPasswordIsNull_shouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> updateUsersPasswordUseCase.execute(new UpdateUsersPasswordCommand(
                "current-password",
                null,
                "confirmation-password",
                "example@domain.com"
        )));

        assertEquals("New password and confirmation password cannot be null.", ex.getMessage());
    }

    @Test
    void whenConfirmationPasswordIsNull_shouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> updateUsersPasswordUseCase.execute(new UpdateUsersPasswordCommand(
                "current-password",
                "new-password",
                null,
                "example@domain.com"
        )));

        assertEquals("New password and confirmation password cannot be null.", ex.getMessage());
    }

    @Test
    void whenConfirmationPasswordDoesNotMatch_shouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> updateUsersPasswordUseCase.execute(new UpdateUsersPasswordCommand(
                "current-password",
                "new-password",
                "new-password-with-a-typo",
                "example@domain.com"
        )));

        assertEquals("Passwords do not match.", ex.getMessage());
    }

    @Test
    void whenUserDoesNotExist_shouldThrowUserNotFoundException() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> updateUsersPasswordUseCase.execute(new UpdateUsersPasswordCommand(
                "current-password",
                "ValidPassword123*",
                "ValidPassword123*",
                "non-existing-email@domain.com"
        )));
    }

    @Test
    void whenCurrentPasswordIsWrong_shouldThrowInvalidFieldException() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> updateUsersPasswordUseCase.execute(new UpdateUsersPasswordCommand(
                "invalid-password",
                "ValidPassword123*",
                "ValidPassword123*",
                "example@domain.com"
        )));

        assertEquals("Current password does not match.", ex.getMessage());
    }

    @Test
    void whenExistingUserAndValidArguments_shouldUpdatePassword() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
        when(encoder.encode(anyString())).thenReturn("ValidPassword123*");
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User result = updateUsersPasswordUseCase.execute(new UpdateUsersPasswordCommand(
                "encoded-hash",
                "ValidPassword123*",
                "ValidPassword123*",
                "example@domain.com"
        ));

        verify(encoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any());

        assertEquals("ValidPassword123*", result.getPassword().getValue());
    }
}
