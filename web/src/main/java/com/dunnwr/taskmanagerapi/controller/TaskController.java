package com.dunnwr.taskmanagerapi.controller;

import com.dunnwr.taskmanagerapi.commands.task.CreateTaskCommand;
import com.dunnwr.taskmanagerapi.dto.task.CreateTaskRequest;
import com.dunnwr.taskmanagerapi.dto.task.TaskResponse;
import com.dunnwr.taskmanagerapi.models.task.Priority;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.usecases.task.CreateTaskUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase){
        this.createTaskUseCase = createTaskUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request){

        // TODO: get the user id from the JWT token, not passed by the user themselves.

        CreateTaskCommand command = new CreateTaskCommand(
                request.title(),
                request.description(),
                Priority.from(request.priority()),
                request.dueDate(),
                request.userId()
        );

        Task createdTask = createTaskUseCase.execute(command);

        TaskResponse response = new TaskResponse(
                createdTask.getId(),
                createdTask.getTitle(),
                createdTask.getDescription(),
                createdTask.getStatus().name(),
                createdTask.getPriority().name(),
                createdTask.getDueDate()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
