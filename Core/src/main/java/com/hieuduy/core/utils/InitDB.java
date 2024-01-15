package com.hieuduy.core.utils;

import com.hieuduy.core.entities.Admin;
import com.hieuduy.core.entities.Role;
import com.hieuduy.core.repositories.AdminRepository;
import com.hieuduy.core.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InitDB implements CommandLineRunner {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = Role.builder().name("ADMIN").build();
        Role customerRole = Role.builder().name("CUSTOMER").build();

        if (!roleRepository.findByName(adminRole.getName()).isPresent()) {
            roleRepository.save(adminRole);
        }

        if (!roleRepository.findByName(customerRole.getName()).isPresent()) {
            roleRepository.save(customerRole);
        }


        Admin admin = Admin.builder()
                .firstName("Nguyen").lastName("HieuHieu").username("hieu@gmail.com")
                .roles(List.of(adminRole))
                .password(passwordEncoder.encode("123123123"))
                .build();
        if (adminRepository.findByUsername(admin.getUsername()) == null) {
            adminRepository.save(admin);
        }

    }
}
