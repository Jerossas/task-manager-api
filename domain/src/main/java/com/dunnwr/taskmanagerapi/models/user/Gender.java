package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.user.InvalidGenderException;

public enum Gender {
    FEMALE, MALE;

    public static Gender from(String value){
        if (value == null) {
            throw new InvalidGenderException("Gender cannot be null.");
        }
        try {
            return Gender.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidGenderException("Invalid gender value: " + value);
        }
    }
}
