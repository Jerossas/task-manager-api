package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.SignUpUserCommand;
import com.dunnwr.taskmanagerapi.exceptions.EmailAlreadyRegisteredException;
import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignUpUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignUpUserUseCaseImpl signUpUserUseCase;

    @Test
    void whenNullPassword_shouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> signUpUserUseCase.execute(new SignUpUserCommand(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                null,
                "ValidPassword123*",
                "example@domain.com",
                "male"
        )));

        assertEquals("Password and confirmation cannot be null.", ex.getMessage());
    }

    @Test
    void whenNullPasswordConfirmation_shouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> signUpUserUseCase.execute(new SignUpUserCommand(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                "ValidPassword123*",
                null,
                "example@domain.com",
                "male"
        )));

        assertEquals("Password and confirmation cannot be null.", ex.getMessage());
    }

    @Test
    void whenPasswordAndConfirmationDoNotMatch_shouldThrowInvalidFieldException() {

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> signUpUserUseCase.execute(new SignUpUserCommand(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                "ValidPassword123**",
                "ValidPassword123*",
                "example@domain.com",
                "male"
        )));

        assertEquals("Passwords do not match.", ex.getMessage());
    }

    @Test
    void usingExistingEmail_shouldThrowEmailAlreadyRegisteredException() {

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(EmailAlreadyRegisteredException.class, () -> signUpUserUseCase.execute(new SignUpUserCommand(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                "ValidPassword123*",
                "ValidPassword123*",
                "example@domain.com",
                "male"
        )));
    }

    @Test
    void whenNewEmailAndValidPassword_shouldSaveNewUser() {

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-hash");

        signUpUserUseCase.execute(new SignUpUserCommand(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                "ValidPassword123*",
                "ValidPassword123*",
                "example@domain.com",
                "male"
        ));

        verify(userRepository, times(1)).save(any());
    }
}
