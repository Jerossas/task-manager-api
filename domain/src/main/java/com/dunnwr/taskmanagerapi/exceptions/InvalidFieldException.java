package com.dunnwr.taskmanagerapi.exceptions;

public class InvalidFieldException extends DomainException {

    private static final int HTTP_CODE = 400;
    private final String field;

    public InvalidFieldException(String field, String message) {
        super(message, HTTP_CODE);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
