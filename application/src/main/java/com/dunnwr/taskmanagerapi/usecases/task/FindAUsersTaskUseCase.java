package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.FindAUsersTaskCommand;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface FindAUsersTaskUseCase extends UseCase<FindAUsersTaskCommand, Task> {
}
