package com.hieuduy.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.hieuduy.core.*", "com.hieuduy.customer.*"})
@EnableJpaRepositories(value = "com.hieuduy.core.repositories")
@EntityScan(value = "com.hieuduy.core.entities")
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

}
