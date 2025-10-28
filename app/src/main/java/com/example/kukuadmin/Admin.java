package com.example.kukuadmin;

public class Admin {
    private String adminId;
    private String password;

    // Empty constructor required for Firebase
    public Admin() {
    }

    // Constructor to initialize fields
    public Admin(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

    // Getters and Setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
