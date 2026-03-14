package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.user.InvalidPasswordException;

public class Password {

    private final String password;

    public Password(String password){

        if (password == null) {
            throw new InvalidPasswordException("Password cannot be null.");
        }

        if (password.isBlank()) {
            throw new InvalidPasswordException("Password cannot be empty.");
        }

        String regularExpression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if(!password.matches(regularExpression)){
            throw new InvalidPasswordException("Password does not meet requirements.");
        }

        this.password = password;
    }

    public String getValue(){
        return this.password;
    }
}
