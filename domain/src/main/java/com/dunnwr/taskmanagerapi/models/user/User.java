package com.dunnwr.taskmanagerapi.models.user;

import com.dunnwr.taskmanagerapi.exceptions.InvalidFieldException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Password password;
    private final Email email;
    private Gender gender;
    private final Set<Role> roles;

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
        this.roles = new HashSet<>(Set.of(Role.USER));
    }

    private User(Long id, String firstName, String middleName, String lastName, Password password, Email email, Gender gender, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.roles = roles;
    }

    public static User restore(Long id, String firstName, String middleName, String lastName, Password password, Email email, Gender gender, Set<Role> roles){
        return new User(
                id,
                firstName,
                middleName,
                lastName,
                password,
                email,
                gender,
                roles
        );
    }

    public Long getId(){
        return this.id;
    }

    public Set<Role> getRoles(){
        return Collections.unmodifiableSet(this.roles);
    }

    public void addRole(Role newRole){

        if(newRole == null){
            throw new InvalidFieldException("newRole", "Role cannot be null");
        }

        roles.add(newRole);
    }

    public void removeRole(Role role) {
        if (role == null) {
            throw new InvalidFieldException("role", "Role cannot be null.");
        }
        if (role == Role.USER) {
            throw new InvalidFieldException("role", "Cannot remove the USER role.");
        }
        roles.remove(role);
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
