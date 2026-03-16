package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.SignInUserCommand;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface SignInUserUseCase extends UseCase<SignInUserCommand, User> {
}
