package com.dunnwr.taskmanagerapi.repositories;

import com.dunnwr.taskmanagerapi.models.task.Task;

public interface TaskRepository {

    Task save(Task task);
}
