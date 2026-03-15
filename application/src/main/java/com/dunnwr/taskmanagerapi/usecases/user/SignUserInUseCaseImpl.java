package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.SignUserInCommand;
import com.dunnwr.taskmanagerapi.exceptions.EmailAlreadyRegisteredException;
import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.Gender;
import com.dunnwr.taskmanagerapi.models.user.Password;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;

public class SignUserInUseCaseImpl implements SignUserInUseCase {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public SignUserInUseCaseImpl(PasswordEncoder encoder, UserRepository userRepository){
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public User execute(SignUserInCommand command){

        if (command.password() == null || command.confirmPassword() == null) {
            throw new InvalidFieldException("password", "Password and confirmation cannot be null.");
        }

        if (!command.password().equals(command.confirmPassword())) {
            throw new InvalidFieldException("confirmPassword", "Passwords do not match.");
        }

        Password.validate(command.password());

        Email userEmail = new Email(command.email());

        if(userRepository.existsByEmail(userEmail)) {
            throw new EmailAlreadyRegisteredException("Email " + userEmail.getValue() + " is already registered. Try with a different one.");
        }

        User newUser = new User(
                command.firstName(),
                command.middleName(),
                command.lastName(),
                Password.fromEncoded(encoder.encode(command.password())),
                userEmail,
                Gender.from(command.gender())
        );

        return userRepository.save(newUser);
    }
}
