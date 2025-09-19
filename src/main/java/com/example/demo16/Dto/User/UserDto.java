package com.example.demo16.Dto.User;

import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private Integer userId;
    private String username;
    private String email;
    private String phone;

    // Show roles as strings
    private Set<String> roles = new HashSet<>();

    public UserDto() {}

    public UserDto(Integer userId, String username, String email, String phone, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    // --- Getters & Setters ---
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
