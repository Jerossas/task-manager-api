package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.EditUsersTaskCommand;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface EditAUsersTaskUseCase extends UseCase<EditUsersTaskCommand, Task> {
}
