package com.hieuduy.core.services.impl;

import com.hieuduy.core.dto.AdminDto;
import com.hieuduy.core.entities.Admin;
import com.hieuduy.core.repositories.AdminRepository;
import com.hieuduy.core.repositories.RoleRepository;
import com.hieuduy.core.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = Admin.builder()
                .firstName(adminDto.getFirstName()).lastName(adminDto.getLastName())
                .username(adminDto.getUsername()).password(passwordEncoder.encode(adminDto.getPassword()))
                .roles(Collections.singletonList(roleRepository.findByName("ADMIN").get()))
                .build();
        return adminRepository.save(admin);
    }
}
