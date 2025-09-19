package com.example.demo16.Service.User.Impl;

import com.example.demo16.Dto.User.PermissionDto;
import com.example.demo16.Entity.User.Permission;
import com.example.demo16.Repository.User.PermissionRepository;
import com.example.demo16.Service.User.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public PermissionDto createPermission(PermissionDto permissionDto) {
        // Convert DTO to Entity
        Permission permission = new Permission();
        permission.setPermissionKey(permissionDto.getPermissionKey());
        permission.setDescription(permissionDto.getDescription());

        // Save Entity
        Permission savedPermission = permissionRepository.save(permission);
//
//        // Convert Entity back to DTO
//        PermissionDto savedDto = new PermissionDto();
//        savedDto.setPermissionId(savedPermission.getPermissionId());
//        savedDto.setPermissionKey(savedPermission.getPermissionKey());
//        savedDto.setDescription(savedPermission.getDescription());
//
//        return savedDto;
        return permissionDto;
    }



//    @Override
//    public List<PermissionDto> getAllPermissions() {
//        // Fetch all permissions
//        List<Permission> permissions = permissionRepository.findAll();
//
//        // Debug log
//        System.out.println("Permissions fetched from DB: " + (permissions != null ? permissions.size() : "null"));
//
//        if (permissions == null || permissions.isEmpty()) {
//            System.out.println("No permissions found in the database!");
//            return List.of(); // return empty list instead of null
//        }
//
//        // Map entities to DTOs
//        List<PermissionDto> permissionDtos = permissions.stream()
//                .map(p -> new PermissionDto(
//                        p.getPermissionId(),
//                        p.getPermissionKey(),
//                        p.getDescription()
//                ))
//                .toList();
//
//        // Debug log
//        System.out.println("Mapped DTOs: " + permissionDtos.size());
//
//        return permissionDtos;
//    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }


    @Override
    public void createPermissionsBulk(List<PermissionDto> permissionDtos) {
        List<Permission> permissions = permissionDtos.stream()
                .map(dto -> {
                    Permission permission = new Permission();
                    permission.setPermissionKey(dto.getPermissionKey());
                    permission.setDescription(dto.getDescription());
                    return permission;
                })
                .toList();

        permissionRepository.saveAll(permissions); // Save all at once
    }

}
