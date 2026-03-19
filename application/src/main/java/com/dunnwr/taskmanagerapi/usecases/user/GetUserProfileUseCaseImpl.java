package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;

public class GetUserProfileUseCaseImpl implements GetUserProfileUseCase {

    private final UserRepository userRepository;

    public GetUserProfileUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(String input) {
        return userRepository.findByEmail(Email.of(input))
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}
