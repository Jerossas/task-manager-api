package com.dunnwr.taskmanagerapi.config;

import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import com.dunnwr.taskmanagerapi.usecases.task.CreateTaskUseCase;
import com.dunnwr.taskmanagerapi.usecases.task.CreateTaskUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository){
        return new CreateTaskUseCaseImpl(taskRepository);
    }
}
