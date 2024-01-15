package com.hieuduy.core.services;

import com.hieuduy.core.dto.AdminDto;
import com.hieuduy.core.entities.Admin;

public interface AdminService {
    Admin findByUsername(String username);
    Admin save(AdminDto adminDto);
}
