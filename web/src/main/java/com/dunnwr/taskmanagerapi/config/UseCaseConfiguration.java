package com.dunnwr.taskmanagerapi.config;

import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.repositories.UserRepository;
import com.dunnwr.taskmanagerapi.services.PasswordEncoder;
import com.dunnwr.taskmanagerapi.usecases.task.CreateTaskUseCase;
import com.dunnwr.taskmanagerapi.usecases.task.CreateTaskUseCaseImpl;
import com.dunnwr.taskmanagerapi.usecases.user.SignInUserUseCase;
import com.dunnwr.taskmanagerapi.usecases.user.SignInUserUseCaseImpl;
import com.dunnwr.taskmanagerapi.usecases.user.SignUpUserUseCase;
import com.dunnwr.taskmanagerapi.usecases.user.SignUpUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository){
        return new CreateTaskUseCaseImpl(taskRepository);
    }

    @Bean
    public SignUpUserUseCase signUpUserUseCase(PasswordEncoder encoder, UserRepository userRepository){
        return new SignUpUserUseCaseImpl(encoder, userRepository);
    }

    @Bean
    public SignInUserUseCase signInUserUseCase(PasswordEncoder encoder, UserRepository userRepository){
        return new SignInUserUseCaseImpl(userRepository, encoder);
    }
}
