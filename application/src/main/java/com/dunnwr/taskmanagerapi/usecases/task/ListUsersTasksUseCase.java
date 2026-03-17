package com.dunnwr.taskmanagerapi.usecases.task;

import com.dunnwr.taskmanagerapi.commands.task.ListUsersTasksCommand;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

import java.util.List;

public interface ListUsersTasksUseCase extends UseCase<ListUsersTasksCommand, List<Task>> {
}
