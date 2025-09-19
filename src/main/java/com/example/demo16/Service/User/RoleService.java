package com.example.demo16.Service.User;

import com.example.demo16.Dto.User.CreateRoleDto;
import com.example.demo16.Dto.User.RoleDto;
import com.example.demo16.Entity.User.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Role createRole(CreateRoleDto createRoleDto);

    List<Role> getAllRoles();

    Role assignPermissionToRole(Integer roleId, Set<Integer> permissionIds);
}
