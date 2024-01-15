package com.hieuduy.customer.security;

import com.hieuduy.core.entities.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomerDetails implements UserDetails{
    private Customer customer;
    public CustomerDetails(Customer customer){
        this.customer=customer;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.customer.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
    }

    @Override
    public String getPassword() {
        return this.customer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getUsername();
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
