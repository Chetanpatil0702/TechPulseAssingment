package com.example.demo16.Controller.User;

import com.example.demo16.Dto.User.CreateRoleDto;
import com.example.demo16.Dto.User.RolePermmissionRequest;
import com.example.demo16.Entity.User.Role;
import com.example.demo16.Responce.GenericResponse;
import com.example.demo16.Service.User.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // --- Create a new role ---
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE:CREATE')")
    public ResponseEntity<GenericResponse> createRole(@Valid @RequestBody CreateRoleDto createRoleDto) {
        Role savedRole = roleService.createRole(createRoleDto);
        return ResponseEntity.ok(new GenericResponse(true, "Role created successfully!"));
    }

    // --- Get all roles ---
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE:READ_ALL')")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // --- Assign permissions to a role ---
    @PostMapping("/{roleId}/assign-permissions")
    @PreAuthorize("hasAuthority('ROLE:ASSIGN_PERMISSIONS')")
    public ResponseEntity<GenericResponse> assignPermissions(
            @PathVariable("roleId") Integer roleId,
            @RequestBody RolePermmissionRequest request) {

        try {
            Role updatedRole = roleService.assignPermissionToRole(roleId, request.getPermissionIds());
            return ResponseEntity.ok(new GenericResponse(true,
                    "Permissions assigned successfully to role: " + updatedRole.getRoleName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new GenericResponse(false, "Failed to assign permissions: " + e.getMessage()));
        }
    }

}
