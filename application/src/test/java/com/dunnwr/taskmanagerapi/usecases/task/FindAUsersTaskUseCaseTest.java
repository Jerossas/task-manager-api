package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.FindAUsersTaskCommand;
import com.dunnwr.taskmanagerapi.exceptions.TaskNotFoundException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.task.Priority;
import com.dunnwr.taskmanagerapi.models.task.Status;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.models.user.*;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAUsersTaskUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private FindAUsersTaskUseCaseImpl findAUsersTaskUseCase;

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

    private Task createValidTask() {
        return Task.restore(
                1L,
                "Estudiar",
                "Estudiar mucho",
                Status.from("new"),
                Priority.from("high"),
                1L,
                LocalDateTime.now().plusDays(10)
        );
    }

    @Test
    void whenUserDoesNotExist_shouldThrowUserNotFoundException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> findAUsersTaskUseCase.execute(new FindAUsersTaskCommand(
                "non-existing-email@domain.com",
                1L
        )));
    }

    @Test
    void whenTaskDoesNotExist_shouldThrowTaskNotFoundException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(taskRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> findAUsersTaskUseCase.execute(new FindAUsersTaskCommand(
                "example@domain.com",
                1L
        )));
    }

    @Test
    void whenExistingUserAndValidTask_shouldReturnTask() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(taskRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(createValidTask()));

        Task result = findAUsersTaskUseCase.execute(new FindAUsersTaskCommand(
                "example@domain.com",
                1L
        ));

        assertEquals("Estudiar", result.getTitle());
    }
}
