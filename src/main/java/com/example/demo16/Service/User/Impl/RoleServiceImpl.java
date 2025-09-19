package com.example.demo16.Service.User.Impl;

import com.example.demo16.Dto.User.CreateRoleDto;
import com.example.demo16.Entity.User.Permission;
import com.example.demo16.Entity.User.Role;
import com.example.demo16.Repository.User.PermissionRepository;
import com.example.demo16.Repository.User.RoleRepository;
import com.example.demo16.Service.User.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Role createRole(CreateRoleDto createRoleDto) {
        // Convert DTO to Entity
        Role role = new Role();
        role.setRoleName(createRoleDto.getRoleName());
        role.setDescription(createRoleDto.getDescription());

        // Save to DB
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public Role assignPermissionToRole(Integer roleId, Set<Integer> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()-> new RuntimeException("Role not found"));

        Set<Permission> newPermissions = new HashSet<>(permissionRepository.findAllById(permissionIds));
        role.getPermissions().addAll(newPermissions);
        return roleRepository.save(role);
    }




}
