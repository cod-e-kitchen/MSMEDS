package com.example.entitymappingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {
        "com.example.entitymappingservice",
        "com.example.msmedscommonutil"
})
public class EntityMappingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntityMappingServiceApplication.class, args);
    }
}
