package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public class Password {

    private final String value;

    private Password (String value){
        this.value = value;
    }

    public static void validate(String rawPassword) {

        if (rawPassword == null) {
            throw new InvalidFieldException("password", "Password cannot be null.");
        }

        if (rawPassword.isBlank()) {
            throw new InvalidFieldException("password", "Password cannot be empty.");
        }

        if (rawPassword.length() < 8) {
            throw new InvalidFieldException("password", "Password must be at least 8 characters long.");
        }

        if (!rawPassword.matches(".*[A-Z].*")) {
            throw new InvalidFieldException("password", "Password must contain at least one uppercase letter.");
        }

        if (!rawPassword.matches(".*[a-z].*")) {
            throw new InvalidFieldException("password", "Password must contain at least one lowercase letter.");
        }

        if (!rawPassword.matches(".*\\d.*")) {
            throw new InvalidFieldException("password", "Password must contain at least one number.");
        }

        if (!rawPassword.matches(".*[@$!%*?&].*")) {
            throw new InvalidFieldException("password", "Password must contain at least one special character (@$!%*?&).");
        }

    }

    public static Password fromEncoded(String encodedPassword){

        if (encodedPassword == null) {
            throw new InvalidFieldException("password", "Encoded password cannot be null.");
        }

        return new Password(encodedPassword);
    }

    public String getValue() {
        return this.value;
    }
}