package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.CreateTaskCommand;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;

import java.time.LocalDateTime;

public class CreateTaskUseCaseImpl implements CreateTaskUseCase{

    private final TaskRepository taskRepository;

    public CreateTaskUseCaseImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public Task execute(CreateTaskCommand input) {

        Task newTask = new Task(
                input.title(),
                input.description(),
                input.priority(),
                input.dueDate(),
                input.userId(),
                LocalDateTime.now()
        );

        return this.taskRepository.save(newTask);
    }
}
