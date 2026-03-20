package com.dunnwr.taskmanagerapi.repositories;

import com.dunnwr.taskmanagerapi.models.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);
    List<Task> findByUserId(Long userId);
    Optional<Task> findByIdAndUserId(Long taskId, Long userId);
    void deleteById(Long taskId);
    void deleteByUserId(Long userId);
}
