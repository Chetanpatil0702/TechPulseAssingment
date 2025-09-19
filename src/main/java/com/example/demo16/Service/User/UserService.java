package com.example.demo16.Service.User;

import com.example.demo16.Dto.User.CreateUserDto;
import com.example.demo16.Dto.User.UserDto;
import com.example.demo16.Entity.User.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User createUser(CreateUserDto dto);

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();

    User assignRolesToUser(Integer userId, Set<Integer> roleIds);
}
