package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.CreateTaskCommand;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateTaskUseCaseImpl createTaskUseCase;

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
    void whenUserNotFound_shouldThrowUserNotFoundException() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> createTaskUseCase.execute(
                new CreateTaskCommand(
                        "Estudiar",
                        "Estudiar mucho",
                        "high",
                        LocalDateTime.now().plusDays(10),
                        "example@domain.com"
                )
        ));
    }

    @Test
    void whenExistingUserAndValidTask_shouldSaveTask() {
        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(createValidUser()));

        when(taskRepository.save(any())).thenReturn(Task.restore(
                1L,
                "Estudiar",
                "Estudiar mucho",
                Status.from("new"),
                Priority.from("high"),
                1L,
                LocalDateTime.now().plusDays(10)
        ));

        Task result = createTaskUseCase.execute(new CreateTaskCommand(
                "Estudiar",
                "Estudiar mucho",
                "HIGH",
                LocalDateTime.now().plusDays(10),
                "example@domain.com"
        ));

        assertEquals("Estudiar", result.getTitle());
        verify(taskRepository).save(any());
    }

}
