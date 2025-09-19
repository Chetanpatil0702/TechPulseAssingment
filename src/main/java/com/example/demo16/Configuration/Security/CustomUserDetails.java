package com.example.demo16.Configuration.Security;

import com.example.demo16.Entity.User.Permission;
import com.example.demo16.Entity.User.Role;
import com.example.demo16.Entity.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    @Autowired
    private User user;

    public CustomUserDetails(User user)
    {
        this.user = user;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for(Role role : user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
            for(Permission permission : role.getPermissions())
            {
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionKey()));
            }
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
