package com.dunnwr.taskmanagerapi.models;

import com.dunnwr.taskmanagerapi.exceptions.task.*;
import com.dunnwr.taskmanagerapi.models.Priority;
import com.dunnwr.taskmanagerapi.models.Status;

import java.time.LocalDateTime;

public class Task {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private final Long userId;
    private LocalDateTime dueDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(String title, String description, Priority priority, LocalDateTime dueDate, Long userId, LocalDateTime currentDate){

        changeTitle(title);
        changeDescription(description);
        changePriority(priority);
        changeDueDate(dueDate, currentDate);

        if(userId == null){
            throw new InvalidTaskOwnerException("User id cannot be null");
        }

        this.userId = userId;

        this.status = Status.NEW;

        this.createdAt = this.updatedAt = currentDate;
    }

    public Long getId(){
        return this.id;
    }

    public void changeTitle(String newTitle){

        if(newTitle == null) {
            throw new InvalidTaskTitleException("Title cannot be null.");
        }

        if(newTitle.isBlank()) {
            throw new InvalidTaskTitleException("Title cannot be empty.");
        }

        this.title = newTitle;
        updateModificationDate();
    }

    public String getTitle(){
        return this.title;
    }

    public void changeDescription(String newDescription){

        if(newDescription == null) {
            throw new InvalidDescriptionException("Description cannot be null.");
        }

        if(newDescription.isBlank()) {
            throw new InvalidDescriptionException("Description cannot be empty.");
        }

        this.description = newDescription;
        updateModificationDate();
    }

    public String getDescription(){
        return this.description;
    }

    public Long getOwnerId(){
        return this.userId;
    }

    public void changeDueDate(LocalDateTime newDueDate, LocalDateTime currentTime){

        if(newDueDate == null) {
            throw new InvalidDueDateException("Due date cannot be empty.");
        }

        if(newDueDate.isBefore(currentTime)) {
            throw new InvalidDueDateException("Due date cannot be in the past.");
        }

        this.dueDate = newDueDate;
        updateModificationDate();
    }

    public LocalDateTime getDueDate(){
        return this.dueDate;
    }

    public void changePriority(Priority newPriority){

        if(newPriority == null) {
            throw new InvalidPriorityException("Priority cannot be null.");
        }

        this.priority = newPriority;
        updateModificationDate();
    }

    public Priority getPriority(){
        return this.priority;
    }

    public void changeStatus(Status newStatus){

        if(newStatus == null) {
            throw new InvalidStatusException("Status cannot be null.");
        }

        this.status = newStatus;
        updateModificationDate();
    }

    public Status getStatus(){
        return this.status;
    }

    public LocalDateTime getCreationDate(){
        return this.createdAt;
    }

    private void updateModificationDate(){
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getLastModificationDate(){
        return this.updatedAt;
    }
}
