package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.DeleteUserCommand;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface DeleteUserUseCase extends UseCase<DeleteUserCommand, Void> {
}
