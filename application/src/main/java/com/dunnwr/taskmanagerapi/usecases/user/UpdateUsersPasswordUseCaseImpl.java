package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.UpdateUsersPasswordCommand;
import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.Password;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;

public class UpdateUsersPasswordUseCaseImpl implements UpdateUsersPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUsersPasswordUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(UpdateUsersPasswordCommand command) {

        if (command.newPassword() == null || command.newPasswordConfirmation() == null) {
            throw new InvalidFieldException("newPassword", "New password and confirmation password cannot be null.");
        }

        if (!command.newPassword().equals(command.newPasswordConfirmation())) {
            throw new InvalidFieldException("newPasswordConfirmation", "Passwords do not match.");
        }

        Password.validate(command.newPassword());

        User user = userRepository.findByEmail(Email.of(command.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        if(!passwordEncoder.matches(command.currentPassword(), user.getPassword().getValue())) {
            throw new InvalidFieldException("currentPassword", "Current password does not match.");
        }

        user.changePassword(Password.fromEncoded(passwordEncoder.encode(command.newPassword())));

        return userRepository.save(user);
    }
}
