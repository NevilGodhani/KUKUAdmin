package com.example.kukuadmin;
// User.java
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userUid;

    // Empty constructor required for Firebase
    public User() {
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String userUid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userUid = userUid;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserUid() {
        return userUid;
    }
}

