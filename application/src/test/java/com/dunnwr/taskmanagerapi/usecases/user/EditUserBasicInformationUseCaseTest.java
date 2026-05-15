package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.EditUserBasicInformationCommand;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EditUserBasicInformationUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EditUserBasicInformationUseCaseImpl editUserBasicInformationUseCase;

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
    void whenUserDoesNotExist_shouldReturnUserNotFound() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> editUserBasicInformationUseCase.execute(new EditUserBasicInformationCommand(
                "Joan",
                "Ferley",
                "Mosquera Lozano",
                "male",
                "example@domain.com"
        )));
    }

    @Test
    void whenExistingUser_shouldUpdateInformation() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User result = editUserBasicInformationUseCase.execute(new EditUserBasicInformationCommand(
                "Sofia",
                "Andrea",
                "Ramirez",
                "female",
                "example@domain.com"
        ));

        assertEquals("Sofia", result.getFirstName());
        assertEquals("Andrea", result.getMiddleName());
        assertEquals("Ramirez", result.getLastName());
        assertEquals(Gender.FEMALE, result.getGender());
    }
}
