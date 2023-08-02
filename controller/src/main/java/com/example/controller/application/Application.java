package com.example.controller.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.example.database.entity")
@EnableJpaRepositories("com.example.database.repository")
@SpringBootApplication(scanBasePackages = {"com.example.database",
        "com.example.service", "com.example.controller"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
