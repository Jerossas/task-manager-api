package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.GetUserProfileCommand;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.user.*;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserProfileUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserProfileUseCaseImpl getUserProfileUseCase;

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

        assertThrows(UserNotFoundException.class, () -> getUserProfileUseCase.execute(
                new GetUserProfileCommand("non-existing-email@domain.com")
        ));
    }

    @Test
    void whenExistingUser_shouldReturnUser() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));

        User result = getUserProfileUseCase.execute(new GetUserProfileCommand(
                "example@domain.com"
        ));

        assertNotNull(result);
        assertEquals("example@domain.com", result.getEmail().getValue());
    }

}
