package com.hieuduy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.hieuduy.core.*", "com.hieuduy.admin.*"})
@EnableJpaRepositories(value = "com.hieuduy.core.repositories")
@EntityScan(value = "com.hieuduy.core.entities")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
