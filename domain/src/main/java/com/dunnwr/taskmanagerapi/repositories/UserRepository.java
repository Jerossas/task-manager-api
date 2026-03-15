package com.dunnwr.taskmanagerapi.repositories;

import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;

public interface UserRepository {

    User save(User user);
    boolean existsByEmail(Email email);
}
