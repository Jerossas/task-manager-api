package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.SignInUserCommand;
import com.dunnwr.taskmanagerapi.exceptions.InvalidCredentialsException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignInUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignInUserUseCaseImpl signInUserUseCase;

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
    void whenUserDoesNotExist_shouldThrowInvalidCredentialsException() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        InvalidCredentialsException ex = assertThrows(InvalidCredentialsException.class, () -> signInUserUseCase.execute(new SignInUserCommand(
                "non-existing-email@domain.com",
                "password-for-non-existing-user"
        )));

        assertEquals("Invalid email or password.", ex.getMessage());
    }

    @Test
    void whenIncorrectPassword_shouldThrowInvalidCredentialsException() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        InvalidCredentialsException ex = assertThrows(InvalidCredentialsException.class, () -> signInUserUseCase.execute(new SignInUserCommand(
                "example@domain.com",
                "encoded-hash"
        )));

        assertEquals("Invalid email or password.", ex.getMessage());
    }

    @Test
    void whenValidCredentials_shouldReturnRegisteredUser() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User result = signInUserUseCase.execute(new SignInUserCommand(
                "example@domain.com",
                "encoded-hash"
        ));

        assertNotNull(result);
        assertEquals("Joan", result.getFirstName());
    }

}
