package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.ListUsersTasksCommand;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListUsersTasksUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ListUsersTasksUseCaseImpl listUsersTasksUseCase;

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

        assertThrows(UserNotFoundException.class, () -> listUsersTasksUseCase.execute(new ListUsersTasksCommand(
                "non-existing-email@domain.com"
        )));
    }

    @Test
    void whenExistingEmail_shouldReturnAListOfTasks() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(createValidUser()));
        when(taskRepository.findByUserId(any())).thenReturn(new ArrayList<>(List.of(
                Task.restore(
                        1L,
                        "Estudiar",
                        "Estudiar Spring Boot",
                        Status.from("new"),
                        Priority.from("high"),
                        1L,
                        LocalDateTime.now().plusDays(10)
                ),
                Task.restore(
                        2L,
                        "Hacer la cena",
                        "Cocinar pollo a la plancha con arvejas",
                        Status.from("new"),
                        Priority.from("high"),
                        1L,
                        LocalDateTime.now().plusHours(4)
                )
        )));

        List<Task> result = listUsersTasksUseCase.execute(new ListUsersTasksCommand(
                "example@domain.com"
        ));

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("Hacer la cena")));
    }
}
