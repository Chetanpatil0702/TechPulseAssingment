package com.example.demo16.Controller.User;

import com.example.demo16.Dto.User.CreateUserDto;
import com.example.demo16.Dto.User.UserDto;
import com.example.demo16.Dto.User.UserRoleRequest;
import com.example.demo16.Entity.User.User;
import com.example.demo16.Responce.GenericResponse;
import com.example.demo16.Service.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // --- Create User ---
    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('USER:CREATE')")
    public ResponseEntity<GenericResponse> createUser(@Valid @RequestBody CreateUserDto dto) {
        userService.createUser(dto);
        return ResponseEntity.ok(new GenericResponse(true, "User is Created"));
    }

    // --- Get user by ID ---
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER:READ')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    // --- Get all users ---
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER:READ')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // --- Assign roles to user ---
    @PostMapping("/{userId}/assign-roles")
    @PreAuthorize("hasAuthority('USER:ASSIGN_ROLES')")
    public ResponseEntity<User> assignRolesToUser(
            @PathVariable Integer userId,
            @RequestBody UserRoleRequest request) {

        try {
            User updatedUser = userService.assignRolesToUser(userId, request.getRoleIds());
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
