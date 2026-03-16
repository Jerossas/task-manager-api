package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.SignInUserCommand;
import com.dunnwr.taskmanagerapi.exceptions.InvalidCredentialsException;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;

public class SignInUserUseCaseImpl implements SignInUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignInUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(SignInUserCommand input) {

        User registeredUser = userRepository.findByEmail(Email.of(input.email()))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!this.passwordEncoder.matches(input.password(), registeredUser.getPassword().getValue())){
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return registeredUser;
    }
}
