package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.DeleteUsersTaskCommand;
import com.dunnwr.taskmanagerapi.exceptions.TaskNotFoundException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;

public class DeleteUsersTaskUseCaseImpl implements DeleteUsersTaskUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DeleteUsersTaskUseCaseImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(DeleteUsersTaskCommand input) {

        User user = userRepository.findByEmail(Email.of(input.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Task task = taskRepository.findByIdAndUserId(input.taskId(), user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + input.taskId() + " not found."));

        taskRepository.deleteById(task.getId());

        return null;
    }
}
