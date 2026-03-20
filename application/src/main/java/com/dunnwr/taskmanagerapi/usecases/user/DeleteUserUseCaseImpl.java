package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.DeleteUserCommand;
import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public DeleteUserUseCaseImpl(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Void execute(DeleteUserCommand input) {

        User user = userRepository.findByEmail(Email.of(input.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found."));


        if(!passwordEncoder.matches(input.currentPassword(), user.getPassword().getValue())) {
            throw new InvalidFieldException("currentPassword", "Incorrect current password. Try again.");
        }

        taskRepository.deleteByUserId(user.getId());
        userRepository.deleteById(user.getId());

        return null;
    }
}
