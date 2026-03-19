package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.EditUserBasicInformationCommand;
import com.dunnwr.taskmanagerapi.exceptions.UserNotFoundException;
import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.Gender;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;

public class EditUserBasicInformationUseCaseImpl implements EditUserBasicInformationUseCase {

    private final UserRepository userRepository;

    public EditUserBasicInformationUseCaseImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User execute(EditUserBasicInformationCommand input) {

        User user = userRepository.findByEmail(Email.of(input.email()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if(input.firstName() != null) {
            user.changeFirstName(input.firstName());
        }

        if(input.middleName() != null) {
            user.changeMiddleName(input.middleName());
        }

        if(input.lastName() != null) {
            user.changeLastName(input.lastName());
        }

        if(input.gender() != null) {
            user.changeGender(Gender.from(input.gender()));
        }

        return userRepository.save(user);
    }
}
