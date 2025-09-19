package com.example.demo16.Service.User;

import com.example.demo16.Dto.User.CreateUserDto;
import com.example.demo16.Dto.User.UserDto;
import com.example.demo16.Entity.User.Role;
import com.example.demo16.Entity.User.User;
import com.example.demo16.Exception.ResourceNotFoundException;
import com.example.demo16.Repository.User.RoleRepository;
import com.example.demo16.Repository.User.UserRepository;
import com.example.demo16.Service.User.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService; // System under test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ---- 1. Test createUser ----
    @Test
    void testCreateUser() {
        CreateUserDto dto = new CreateUserDto();
        dto.setUsername("Chetan");
        dto.setEmail("chetan@example.com");
        dto.setPhone("1234567890");
        dto.setPassword("plainPass");

        User savedUser = new User();
        savedUser.setUserId(1);
        savedUser.setUsername("Chetan");
        savedUser.setEmail("chetan@example.com");
        savedUser.setPhone("1234567890");
        savedUser.setPasswordHash("encodedPass");

        when(passwordEncoder.encode("plainPass")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("Chetan", result.getUsername());
        assertEquals("encodedPass", result.getPasswordHash());
        verify(passwordEncoder, times(1)).encode("plainPass");
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ---- 2. Test getUserById (success) ----
    @Test
    void testGetUserById_UserExists() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("Chetan");
        user.setEmail("chetan@example.com");
        user.setPhone("1234567890");

        Role role = new Role();
        role.setRoleId(1);
        role.setRoleName("ADMIN");
        role.setDescription("Admin role");

        user.setRoles(Set.of(role));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDto dto = userService.getUserById(1);

        assertNotNull(dto);
        assertEquals("Chetan", dto.getUsername());
        assertTrue(dto.getRoles().contains("ADMIN"));
        verify(userRepository, times(1)).findById(1);
    }


    // ---- 3. Test getUserById (not found) ----
    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99));
        verify(userRepository, times(1)).findById(99);
    }

    // ---- 4. Test getAllUsers ----
    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUserId(1);
        user1.setUsername("Chetan");
        user1.setEmail("c@example.com");

        User user2 = new User();
        user2.setUserId(2);
        user2.setUsername("Lokesh");
        user2.setEmail("l@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Chetan", result.get(0).getUsername());
        assertEquals("Lokesh", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    // ---- 5. Test assignRolesToUser ----
    @Test
    void testAssignRolesToUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("Chetan");

        Role role = new Role();
        role.setRoleId(10);
        role.setRoleName("ADMIN");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roleRepository.findAllById(Set.of(10))).thenReturn(List.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.assignRolesToUser(1, Set.of(10));

        assertNotNull(result.getRoles());
        assertEquals(1, result.getRoles().size());
        assertEquals("ADMIN", result.getRoles().iterator().next().getRoleName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAssignRolesToUser_UserNotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.assignRolesToUser(99, Set.of(1)));
    }
}


