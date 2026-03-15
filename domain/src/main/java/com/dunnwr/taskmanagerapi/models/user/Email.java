package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public class Email {

    private final String email;

    public Email(String email) {

        if (email == null) {
            throw new InvalidFieldException("email", "Email cannot be null.");
        }

        if (email.isBlank()) {
            throw new InvalidFieldException("email", "Email cannot be empty.");
        }

        if (!email.contains("@")) {
            throw new InvalidFieldException("email", "Email must contain '@'.");
        }

        String[] parts = email.split("@");

        if (parts.length != 2 || parts[0].isBlank()) {
            throw new InvalidFieldException("email", "Email must have a valid local part before '@'.");
        }

        if (!parts[1].contains(".")) {
            throw new InvalidFieldException("email", "Email domain must contain a '.'.");
        }

        if (parts[1].endsWith(".") || parts[1].split("\\.")[parts[1].split("\\.").length - 1].length() < 2) {
            throw new InvalidFieldException("email", "Email must have a valid domain extension (e.g. .com, .co).");
        }

        this.email = email;
    }

    public String getValue() {
        return this.email;
    }
}
