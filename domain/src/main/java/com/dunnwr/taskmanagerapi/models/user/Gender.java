package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public enum Gender {
    FEMALE, MALE;

    public static Gender from(String value){
        if (value == null) {
            throw new InvalidFieldException("gender", "Gender cannot be null.");
        }
        try {
            return Gender.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidFieldException("gender", "Invalid gender value: " + value);
        }
    }
}
