package com.example.demo16.Service.User.Impl;

import com.example.demo16.Configuration.Security.CustomUserDetails;
import com.example.demo16.Entity.User.User;
import com.example.demo16.Exception.ResourceNotFoundException;
import com.example.demo16.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found....!"));
        return new CustomUserDetails(user);
    }
}
