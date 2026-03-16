package com.dunnwr.taskmanagerapi.models.task;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

import java.time.LocalDateTime;

public class Task {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private final Long userId;
    private LocalDateTime dueDate;

    public Task(String title, String description, Priority priority, LocalDateTime dueDate, Long userId, LocalDateTime currentDate){

        changeTitle(title);
        changeDescription(description);
        changePriority(priority);
        changeDueDate(dueDate, currentDate);

        if(userId == null){
            throw new InvalidFieldException("userId", "User id cannot be null");
        }

        this.userId = userId;

        this.status = Status.NEW;
    }

    private Task(Long id, String title, String description, Status status, Priority priority, Long userId, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.userId = userId;
        this.dueDate = dueDate;
    }

    public static Task restore(Long id, String title, String description, Status status, Priority priority, Long userId, LocalDateTime dueDate){
        return new Task(id, title, description, status, priority, userId, dueDate);
    }

    public Long getId(){
        return this.id;
    }

    public void changeTitle(String newTitle){

        if(newTitle == null) {
            throw new InvalidFieldException("title", "Title cannot be null.");
        }

        if(newTitle.isBlank()) {
            throw new InvalidFieldException("title", "Title cannot be empty.");
        }

        this.title = newTitle;
    }

    public String getTitle(){
        return this.title;
    }

    public void changeDescription(String newDescription){

        if(newDescription == null) {
            throw new InvalidFieldException("description","Description cannot be null.");
        }

        if(newDescription.isBlank()) {
            throw new InvalidFieldException("description", "Description cannot be empty.");
        }

        this.description = newDescription;
    }

    public String getDescription(){
        return this.description;
    }

    public Long getOwnerId(){
        return this.userId;
    }

    public void changeDueDate(LocalDateTime newDueDate, LocalDateTime currentTime){

        if(newDueDate == null) {
            throw new InvalidFieldException("dueDate", "Due date cannot be empty.");
        }

        if(newDueDate.isBefore(currentTime)) {
            throw new InvalidFieldException("dueDate", "Due date cannot be in the past.");
        }

        this.dueDate = newDueDate;
    }

    public LocalDateTime getDueDate(){
        return this.dueDate;
    }

    public void changePriority(Priority newPriority){

        if(newPriority == null) {
            throw new InvalidFieldException("priority", "Priority cannot be null.");
        }

        this.priority = newPriority;
    }

    public Priority getPriority(){
        return this.priority;
    }

    public void changeStatus(Status newStatus){

        if(newStatus == null) {
            throw new InvalidFieldException("status", "Status cannot be null.");
        }

        this.status = newStatus;
    }

    public Status getStatus(){
        return this.status;
    }

}
