package com.example.demo16.Dto.User;

import java.util.Set;

public class UserRoleRequest {

    private Set<Integer> roleIds;

    public Set<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
