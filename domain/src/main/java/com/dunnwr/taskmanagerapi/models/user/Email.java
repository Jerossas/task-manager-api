package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.user.InvalidEmailException;

public class Email {

    private final String email;

    public Email(String email){

        if(email == null) {
            throw new InvalidEmailException("Email cannot be null.");
        }

        if(email.isBlank()){
            throw new InvalidEmailException("Email cannot be empty.");
        }

        String regularExpression = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if(!email.matches(regularExpression)) {
            throw new InvalidEmailException("Email does not meet requirements.");
        }

        this.email = email;
    }

    public String getValue(){
        return this.email;
    }
}
