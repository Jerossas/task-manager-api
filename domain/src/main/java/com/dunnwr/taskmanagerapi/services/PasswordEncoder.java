package com.dunnwr.taskmanagerapi.services;

public interface PasswordEncoder {

    String encode(String rawString);
}
