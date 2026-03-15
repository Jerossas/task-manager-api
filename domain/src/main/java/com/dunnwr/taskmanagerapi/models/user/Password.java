package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public class Password {

    private final String password;

    public Password(String password) {

        if (password == null) {
            throw new InvalidFieldException("password", "Password cannot be null.");
        }

        if (password.isBlank()) {
            throw new InvalidFieldException("password", "Password cannot be empty.");
        }

        if (password.length() < 8) {
            throw new InvalidFieldException("password", "Password must be at least 8 characters long.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidFieldException("password", "Password must contain at least one uppercase letter.");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new InvalidFieldException("password", "Password must contain at least one lowercase letter.");
        }

        if (!password.matches(".*\\d.*")) {
            throw new InvalidFieldException("password", "Password must contain at least one number.");
        }

        if (!password.matches(".*[@$!%*?&].*")) {
            throw new InvalidFieldException("password", "Password must contain at least one special character (@$!%*?&).");
        }

        this.password = password;
    }

    public String getValue() {
        return this.password;
    }
}