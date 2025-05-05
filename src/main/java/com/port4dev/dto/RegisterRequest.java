package com.port4dev.dto;

public class RegisterRequest {
    private String email;
    private String password;
    private String name;  // ✅ 추가

    public RegisterRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // ✅ 추가된 name getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}