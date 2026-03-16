package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.SignUpUserCommand;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface SignUpUserUseCase extends UseCase<SignUpUserCommand, User> {
}
