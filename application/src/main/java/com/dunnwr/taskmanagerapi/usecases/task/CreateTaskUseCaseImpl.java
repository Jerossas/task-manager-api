package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.CreateTaskCommand;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.task.Priority;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;

import java.time.LocalDateTime;

public class CreateTaskUseCaseImpl implements CreateTaskUseCase{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CreateTaskUseCaseImpl(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task execute(CreateTaskCommand input) {

        User user = userRepository.findByEmail(Email.of(input.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        Task newTask = new Task(
                input.title(),
                input.description(),
                Priority.from(input.priority()),
                input.dueDate(),
                user.getId(),
                LocalDateTime.now()
        );

        return this.taskRepository.save(newTask);
    }
}
