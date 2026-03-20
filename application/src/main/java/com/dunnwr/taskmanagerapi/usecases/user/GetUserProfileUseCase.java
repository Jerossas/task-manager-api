package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.GetUserProfileCommand;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface GetUserProfileUseCase extends UseCase<GetUserProfileCommand, User> {
}
