package com.example.demo16.Service.User;

import com.example.demo16.Dto.User.PermissionDto;
import com.example.demo16.Entity.User.Permission;

import java.util.List;

public interface PermissionService {

    PermissionDto createPermission(PermissionDto permissionDto);

    List<Permission> getAllPermissions();

    void createPermissionsBulk(List<PermissionDto> permissionDtos);
}
