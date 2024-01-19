package com.hieuduy.core.services.impl;

import com.hieuduy.core.dto.CustomerDto;
import com.hieuduy.core.entities.Customer;
import com.hieuduy.core.repositories.CustomerRepository;
import com.hieuduy.core.repositories.RoleRepository;
import com.hieuduy.core.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .username(customerDto.getUsername())
                .password(passwordEncoder.encode(customerDto.getPassword()))
                .phoneNumber(customerDto.getPhoneNumber())
                .roles(List.of(roleRepository.findByName("CUSTOMER").get()))
                .build();
        return customerRepository.save(customer);
    }
}
