package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.UpdateUsersPasswordCommand;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface UpdateUsersPasswordUseCase extends UseCase<UpdateUsersPasswordCommand, User> {
}
