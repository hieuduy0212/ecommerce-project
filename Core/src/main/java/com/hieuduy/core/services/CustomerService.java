package com.hieuduy.core.services;

import com.hieuduy.core.dto.CustomerDto;
import com.hieuduy.core.entities.Customer;

public interface CustomerService {
    Customer findByUsername(String username);
    Customer save(CustomerDto customerDto);
}
