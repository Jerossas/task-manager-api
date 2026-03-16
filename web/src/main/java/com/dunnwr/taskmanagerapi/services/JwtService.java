package com.dunnwr.taskmanagerapi.services;

import com.dunnwr.taskmanagerapi.models.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generate(User user);
    String extractEmail(String token);
    boolean isValid(String token, UserDetails userDetails);
}
