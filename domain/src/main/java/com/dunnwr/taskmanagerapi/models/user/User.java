package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

public class User {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Password password;
    private final Email email;
    private Gender gender;

    public User(String firstName, String middleName, String lastName, Password password, Email email, Gender gender){

        changeFirstName(firstName);
        changeMiddleName(middleName);
        changeLastName(lastName);
        changePassword(password);
        changeGender(gender);

        if (email == null){
            throw new InvalidFieldException("email", "Email cannot be null");
        }

        this.email = email;
    }

    public Long getId(){
        return this.id;
    }

    public void changeFirstName(String newFirstName){

        if(newFirstName == null) {
            throw new InvalidFieldException("firstName", "First name cannot be null.");
        }

        if(newFirstName.isBlank()) {
            throw new InvalidFieldException("firstName", "First name cannot be empty.");
        }

        this.firstName = newFirstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void changeMiddleName(String newMiddleName){
        this.middleName = newMiddleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void changeLastName(String newLastName){

        if(newLastName == null) {
            throw new InvalidFieldException("lastName", "Last name cannot be null");
        }

        if(newLastName.isBlank()) {
            throw new InvalidFieldException("lastName", "Last name cannot be empty");
        }

        this.lastName = newLastName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void changePassword(Password newPassword){

        if(newPassword == null) {
            throw new InvalidFieldException("password", "The password cannot be null");
        }

        this.password = newPassword;
    }

    public Password getPassword() {
        return password;
    }

    public Email getEmail(){
        return this.email;
    }

    public void changeGender(Gender newGender){

        if(newGender == null){
            throw new InvalidFieldException("gender", "Gender cannot be null.");
        }

        this.gender = newGender;
    }

    public Gender getGender(){
        return this.gender;
    }
}
