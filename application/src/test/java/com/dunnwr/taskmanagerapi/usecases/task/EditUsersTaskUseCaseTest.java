package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.EditUsersTaskCommand;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditUsersTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EditAUsersTaskUseCaseImpl editAUsersTaskUseCase;

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

        assertThrows(UserNotFoundException.class, () -> editAUsersTaskUseCase.execute(new EditUsersTaskCommand(
                1L,
                "Estudiar",
                "Estudiar mucho",
                "new",
                "high",
                LocalDateTime.now().plusDays(10),
                "non-existing-email@domain.com"
        )));
    }

    @Test
    void whenTaskDoesNotExist_shouldThrowTaskNotFoundException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(taskRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> editAUsersTaskUseCase.execute(new EditUsersTaskCommand(
                1L,
                "Estudiar",
                "Estudiar mucho",
                "new",
                "high",
                LocalDateTime.now().plusDays(10),
                "example@domain.com"
        )));
    }

    @Test
    void whenExistingUserAndValidTask_ShouldUpdateTask() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(taskRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(createValidTask()));
        when(taskRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = editAUsersTaskUseCase.execute(new EditUsersTaskCommand(
                1L,
                "Estudiar Spring Boot",
                "Estudiar mucho",
                "new",
                "high",
                LocalDateTime.now().plusDays(10),
                "example@domain.com"
        ));

        assertEquals("Estudiar Spring Boot", result.getTitle());

        verify(taskRepository, times(1)).save(any());
    }

}
