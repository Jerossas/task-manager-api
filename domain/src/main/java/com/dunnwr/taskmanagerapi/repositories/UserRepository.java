package com.dunnwr.taskmanagerapi.repositories;

import com.dunnwr.taskmanagerapi.models.user.Email;
import com.dunnwr.taskmanagerapi.models.user.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);
    boolean existsByEmail(Email email);
    Optional<User> findByEmail(Email email);
    void deleteById(Long id);
}
