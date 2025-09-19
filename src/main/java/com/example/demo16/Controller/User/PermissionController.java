package com.example.demo16.Controller.User;

import com.example.demo16.Dto.User.PermissionDto;
import com.example.demo16.Entity.User.Permission;
import com.example.demo16.Responce.GenericResponse;
import com.example.demo16.Service.User.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // --- Create a new permission ---
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('PERMISSION:CREATE')")
    public ResponseEntity<GenericResponse> createPermission(@Valid @RequestBody PermissionDto permissionDto) {
        permissionService.createPermission(permissionDto);
        return ResponseEntity.ok(new GenericResponse(true, "Permission Created"));
    }


    // --- Get all permissions ---
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PERMISSION:READ_ALL')")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }


    // --- Create multiple permissions in bulk ---
    @PostMapping("/bulk-create")
    @PreAuthorize("hasAuthority('PERMISSION:CREATE')")
    public ResponseEntity<GenericResponse> createPermissionsBulk(
            @Valid @RequestBody List<PermissionDto> permissionDtos) {

        permissionService.createPermissionsBulk(permissionDtos);
        return ResponseEntity.ok(new GenericResponse(true, "Permissions created successfully in bulk"));
    }
}
