package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.user.ListUsersTasksCommand;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;

import java.util.List;

public class ListUsersTasksUseCaseImpl implements ListUsersTasksUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ListUsersTasksUseCaseImpl(TaskRepository taskRepository, UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Task> execute(ListUsersTasksCommand command) {

        User user = userRepository.findByEmail(Email.of(command.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        Long userId = user.getId();

        return taskRepository.findByUserId(userId);
    }
}
