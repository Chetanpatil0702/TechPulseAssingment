package com.example.demo16.Service.User;


import com.example.demo16.Dto.User.PermissionDto;
import com.example.demo16.Entity.User.Permission;
import com.example.demo16.Repository.User.PermissionRepository;
import com.example.demo16.Service.User.Impl.PermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PermissionServiceImplTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //  Test createPermission()
    @Test
    void testCreatePermission() {
        PermissionDto dto = new PermissionDto(null, "user:create", "Create user permission");

        Permission savedEntity = new Permission();
        savedEntity.setPermissionId(1);
        savedEntity.setPermissionKey("user:create");
        savedEntity.setDescription("Create user permission");

        when(permissionRepository.save(any(Permission.class))).thenReturn(savedEntity);

        // Call the method (no return)
        permissionService.createPermission(dto);

        // Verify save() was called
        verify(permissionRepository, times(1)).save(any(Permission.class));
    }


    //  Test getAllPermissions()
    @Test
    void testGetAllPermissions() {
        Permission p1 = new Permission();
        p1.setPermissionId(1);
        p1.setPermissionKey("user:view");
        p1.setDescription("View users");

        Permission p2 = new Permission();
        p2.setPermissionId(2);
        p2.setPermissionKey("user:delete");
        p2.setDescription("Delete users");

        when(permissionRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Permission> result = permissionService.getAllPermissions();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPermissionKey()).isEqualTo("user:view");
        assertThat(result.get(1).getPermissionKey()).isEqualTo("user:delete");

        verify(permissionRepository, times(1)).findAll();
    }

    //  Test createPermissionsBulk()
    @Test
    void testCreatePermissionsBulk() {
        List<PermissionDto> dtos = Arrays.asList(
                new PermissionDto(null, "role:assign", "Assign roles"),
                new PermissionDto(null, "role:delete", "Delete roles")
        );

        permissionService.createPermissionsBulk(dtos);

        verify(permissionRepository, times(1)).saveAll(any());
    }
}

