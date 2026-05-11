package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.DeleteUsersTaskCommand;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUsersTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUsersTaskUseCaseImpl deleteUsersTaskUseCase;

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
    void whenUserDoesNotExit_shouldThrowUserNotFoundException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> deleteUsersTaskUseCase.execute(new DeleteUsersTaskCommand(
                1L,
                "no-existing-email@domain.com"
        )));
    }

    @Test
    void whenTaskDoesNotExist_shouldThrowTaskNotFoundException() {

        when(userRepository.findByEmail(any())).
                thenReturn(Optional.of(createValidUser()));
        when(taskRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> deleteUsersTaskUseCase.execute(new DeleteUsersTaskCommand(
                1L,
                "existing-email@domain.com"
        )));
    }

    @Test
    void whenTaskExistsForUser_shouldDeleteTask() {
        when(userRepository.findByEmail(any())).
                thenReturn(Optional.of(createValidUser()));

        when(taskRepository.findByIdAndUserId(any(), any()))
                .thenReturn(Optional.of(createValidTask()));

        deleteUsersTaskUseCase.execute(new DeleteUsersTaskCommand(
                1L,
                "example@domain.com"
        ));

        verify(taskRepository, times(1)).deleteById(1L);
    }

}
