package com.example.demo16.Dto.User;

import java.util.Set;

public class RolePermmissionRequest {

    private Set<Integer> permissionIds;

    public Set<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
