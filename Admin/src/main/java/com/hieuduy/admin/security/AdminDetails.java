package com.hieuduy.admin.security;

import com.hieuduy.core.entities.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AdminDetails implements UserDetails {
    private Admin admin;
    public Admin getAdmin(){
        return admin;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return admin.getRoles().stream().map(o -> new SimpleGrantedAuthority(o.getName())).toList();
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
