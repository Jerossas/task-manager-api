package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.EditUsersTaskCommand;
import com.dunnwr.taskmanagerapi.exceptions.TaskNotFoundException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.task.Priority;
import com.dunnwr.taskmanagerapi.models.task.Status;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;

import java.time.LocalDateTime;

public class EditAUsersTaskUseCaseImpl implements EditAUsersTaskUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public EditAUsersTaskUseCaseImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task execute(EditUsersTaskCommand command) {

        User user = userRepository.findByEmail(Email.of(command.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        Task task = taskRepository.findByIdAndUserId(command.taskId(), user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + command.taskId() + " not found."));

        if(command.title() != null) {
            task.changeTitle(command.title());
        }

        if(command.description() != null) {
            task.changeDescription(command.description());
        }

        if(command.status() != null) {
            task.changeStatus(Status.from(command.status()));
        }

        if(command.priority() != null) {
            task.changePriority(Priority.from(command.priority()));
        }

        if(command.dueDate() != null) {
            task.changeDueDate(command.dueDate(), LocalDateTime.now());
        }

        return taskRepository.save(task);
    }
}
