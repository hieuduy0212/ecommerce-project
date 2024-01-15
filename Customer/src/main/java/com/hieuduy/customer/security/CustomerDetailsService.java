package com.hieuduy.customer.security;

import com.hieuduy.core.entities.Admin;
import com.hieuduy.core.entities.Customer;
import com.hieuduy.core.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if(customer == null){
            throw new UsernameNotFoundException("Could not find username");
        }
        return new User(customer.getUsername(), customer.getPassword(), customer.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList());
    }
}
