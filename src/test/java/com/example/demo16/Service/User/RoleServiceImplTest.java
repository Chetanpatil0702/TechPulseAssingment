package com.example.demo16.Service.User;

import com.example.demo16.Dto.User.CreateRoleDto;
import com.example.demo16.Entity.User.Permission;
import com.example.demo16.Entity.User.Role;
import com.example.demo16.Repository.User.PermissionRepository;
import com.example.demo16.Repository.User.RoleRepository;
import com.example.demo16.Service.User.Impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---- 1. Test createRole ----
    @Test
    void testCreateRole() {
        CreateRoleDto dto = new CreateRoleDto();
        dto.setRoleName("ADMIN");
        dto.setDescription("Administrator role");

        Role savedRole = new Role();
        savedRole.setRoleId(1);
        savedRole.setRoleName("ADMIN");
        savedRole.setDescription("Administrator role");

        when(roleRepository.save(any(Role.class))).thenReturn(savedRole);

        Role result = roleService.createRole(dto);

        assertNotNull(result);
        assertEquals("ADMIN", result.getRoleName());
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    // ---- 2. Test getAllRoles ----
    @Test
    void testGetAllRoles() {
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setRoleName("ADMIN");

        Role role2 = new Role();
        role2.setRoleId(2);
        role2.setRoleName("USER");

        when(roleRepository.findAll()).thenReturn(List.of(role1, role2));

        List<Role> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getRoleName());
        assertEquals("USER", result.get(1).getRoleName());
        verify(roleRepository, times(1)).findAll();
    }

    // ---- 3. Test assignPermissionToRole (success) ----
    @Test
    void testAssignPermissionToRole() {
        Role role = new Role();
        role.setRoleId(1);
        role.setRoleName("ADMIN");

        Permission permission = new Permission();
        permission.setPermissionId(100);
        permission.setPermissionKey("user:create");

        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(permissionRepository.findAllById(Set.of(100))).thenReturn(List.of(permission));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        Role result = roleService.assignPermissionToRole(1, Set.of(100));

        assertEquals(1, result.getPermissions().size());
        assertTrue(result.getPermissions().stream()
                .anyMatch(p -> p.getPermissionKey().equals("user:create")));
        verify(roleRepository, times(1)).save(role);
    }

    // ---- 4. Test assignPermissionToRole (role not found) ----
    @Test
    void testAssignPermissionToRole_RoleNotFound() {
        when(roleRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> roleService.assignPermissionToRole(99, Set.of(1)));
    }
}

