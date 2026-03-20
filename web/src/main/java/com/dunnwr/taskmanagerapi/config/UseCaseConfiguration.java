package com.dunnwr.taskmanagerapi.config;

import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;
import com.dunnwr.taskmanagerapi.usecases.task.*;
import com.dunnwr.taskmanagerapi.usecases.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository, UserRepository userRepository) {
        return new CreateTaskUseCaseImpl(taskRepository, userRepository);
    }

    @Bean
    public SignUpUserUseCase signUpUserUseCase(PasswordEncoder encoder, UserRepository userRepository) {
        return new SignUpUserUseCaseImpl(encoder, userRepository);
    }

    @Bean
    public SignInUserUseCase signInUserUseCase(PasswordEncoder encoder, UserRepository userRepository) {
        return new SignInUserUseCaseImpl(userRepository, encoder);
    }

    @Bean
    public ListUsersTasksUseCase listUsersTasksUseCase(TaskRepository taskRepository, UserRepository userRepository) {
        return new ListUsersTasksUseCaseImpl(taskRepository, userRepository);
    }

    @Bean
    public FindAUsersTaskUseCase findAUsersTaskUseCase(TaskRepository taskRepository, UserRepository userRepository) {
        return new FindAUsersTaskUseCaseImpl(taskRepository, userRepository);
    }

    @Bean
    public EditAUsersTaskUseCase editAUsersTaskUseCase(TaskRepository taskRepository, UserRepository userRepository) {
        return new EditAUsersTaskUseCaseImpl(taskRepository, userRepository);
    }

    @Bean
    public DeleteUsersTaskUseCase deleteUsersTaskUseCase(TaskRepository taskRepository, UserRepository userRepository){
        return new DeleteUsersTaskUseCaseImpl(taskRepository, userRepository);
    }

    @Bean
    public EditUserBasicInformationUseCase editBasicUserInformationUseCase(UserRepository userRepository) {
        return new EditUserBasicInformationUseCaseImpl(userRepository);
    }

    @Bean
    public GetUserProfileUseCase getUserProfileUseCase(UserRepository userRepository) {
        return new GetUserProfileUseCaseImpl(userRepository);
    }

    @Bean
    public UpdateUsersPasswordUseCase updateUsersPasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UpdateUsersPasswordUseCaseImpl(userRepository, passwordEncoder);
    }
}
