package com.dunnwr.taskmanagerapi.usecases;

public interface UseCase<I, O> {
    O execute(I input);
}
