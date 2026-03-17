package com.dunnwr.taskmanagerapi.repositories;

import com.dunnwr.taskmanagerapi.models.task.Task;

import java.util.List;

public interface TaskRepository {

    Task save(Task task);
    List<Task> findByUserId(Long userId);
}
