package com.dunnwr.taskmanagerapi.controllers;

import com.dunnwr.taskmanagerapi.commands.task.*;
import com.dunnwr.taskmanagerapi.dto.task.CreateTaskRequest;
import com.dunnwr.taskmanagerapi.dto.task.EditTaskRequest;
import com.dunnwr.taskmanagerapi.dto.task.TaskResponse;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.usecases.task.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final ListUsersTasksUseCase listUsersTasksUseCase;
    private final FindAUsersTaskUseCase findAUsersTaskUseCase;
    private final EditAUsersTaskUseCase editAUsersTaskUseCase;
    private final DeleteUsersTaskUseCase deleteUsersTaskUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            ListUsersTasksUseCase listUsersTasksUseCase,
            FindAUsersTaskUseCase findAUsersTaskUseCase,
            EditAUsersTaskUseCase editAUsersTaskUseCase,
            DeleteUsersTaskUseCase deleteUsersTaskUseCase
    ){
        this.createTaskUseCase = createTaskUseCase;
        this.listUsersTasksUseCase = listUsersTasksUseCase;
        this.findAUsersTaskUseCase = findAUsersTaskUseCase;
        this.editAUsersTaskUseCase = editAUsersTaskUseCase;
        this.deleteUsersTaskUseCase = deleteUsersTaskUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request, @AuthenticationPrincipal UserDetails userDetails){

        CreateTaskCommand command = new CreateTaskCommand(
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate(),
                userDetails.getUsername()
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

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAllTasks(@AuthenticationPrincipal UserDetails userDetails){

        ListUsersTasksCommand command = new ListUsersTasksCommand(userDetails.getUsername());

        List<Task> tasks = listUsersTasksUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(tasks.stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus().name(),
                        task.getPriority().name(),
                        task.getDueDate()
                )).toList());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> findTask(@PathVariable("taskId") Long taskId, @AuthenticationPrincipal UserDetails userDetails){

        FindAUsersTaskCommand command = new FindAUsersTaskCommand(userDetails.getUsername(), taskId);

        Task task = findAUsersTaskUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus().name(),
                        task.getPriority().name(),
                        task.getDueDate()
                ));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId, @AuthenticationPrincipal UserDetails userDetails) {

        DeleteUsersTaskCommand command = new DeleteUsersTaskCommand(taskId, userDetails.getUsername());

        deleteUsersTaskUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> editTask(@PathVariable("taskId") Long taskId, @RequestBody EditTaskRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        EditUsersTaskCommand command = new EditUsersTaskCommand(
                taskId,
                request.title(),
                request.description(),
                request.status(),
                request.priority(),
                request.dueDate(),
                userDetails.getUsername()
        );

        Task task = editAUsersTaskUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getDueDate()
        ));
    }
}
