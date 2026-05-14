package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.DeleteUserCommand;
import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.user.*;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
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
public class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

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
    void whenUserDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.execute(new DeleteUserCommand(
                "password-for-non-existing-user",
                "non-existing-user@domain.com"
        )));
    }

    @Test
    void whenWrongPassword_shouldThrowInvalidFieldException(){

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        InvalidFieldException ex = assertThrows(InvalidFieldException.class, () -> deleteUserUseCase.execute(new DeleteUserCommand(
           "bad-password",
           "example@domain.com"
        )));

        assertEquals("Incorrect current password. Try again.", ex.getMessage());
    }

    @Test
    void whenExistingUserAndValidPasswordShouldDeleteTasksAndUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        deleteUserUseCase.execute(new DeleteUserCommand(
                "valid-password",
                "example@domain.com"
        ));

        verify(taskRepository, times(1)).deleteByUserId(any());
        verify(userRepository, times(1)).deleteById(any());
    }
}
