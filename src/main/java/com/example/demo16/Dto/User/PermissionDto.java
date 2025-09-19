package com.example.demo16.Dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionDto {

    private Integer permissionId;

    @NotBlank(message = "Permission key is required")
    @Size(min = 3, max = 100, message = "Permission key must be between 3 and 100 characters")
    private String permissionKey;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    public PermissionDto() {}

    public PermissionDto(Integer permissionId, String permissionKey, String description) {
    }

    // --- Getters and Setters ---
    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
