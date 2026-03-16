package com.dunnwr.taskmanagerapi.services;

public interface PasswordEncoder {

    String encode(String rawString);
    boolean matches(String rawPassword, String encodedPassword);
}
