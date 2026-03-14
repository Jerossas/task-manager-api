package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.CreateTaskCommand;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface CreateTaskUseCase extends UseCase<CreateTaskCommand, Task> {
}
