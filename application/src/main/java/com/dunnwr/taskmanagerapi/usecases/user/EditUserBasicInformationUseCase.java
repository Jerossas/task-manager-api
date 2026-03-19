package com.dunnwr.taskmanagerapi.usecases.user;

import com.dunnwr.taskmanagerapi.commands.user.EditUserBasicInformationCommand;
import com.dunnwr.taskmanagerapi.models.user.User;
import com.dunnwr.taskmanagerapi.usecases.UseCase;

public interface EditUserBasicInformationUseCase extends UseCase<EditUserBasicInformationCommand, User> {
}
