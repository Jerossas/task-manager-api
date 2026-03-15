package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.SignUserInCommand;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface SignUserInUseCase extends UseCase<SignUserInCommand, User> {
}
