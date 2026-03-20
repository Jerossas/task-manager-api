package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.FindAUsersTaskCommand;
import com.dunnwr.taskmanagerapi.exceptions.TaskNotFoundException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;

public class FindAUsersTaskUseCaseImpl implements FindAUsersTaskUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public FindAUsersTaskUseCaseImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task execute(FindAUsersTaskCommand command) {
        User user = userRepository.findByEmail(Email.of(command.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return taskRepository.findByIdAndUserId(command.taskId(), user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + command.taskId() + " not found."));
    }
}
