package com.hanifa.peminjaman_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.hanifa.peminjaman_service")
@EnableDiscoveryClient
// Pisahkan repo JPA dan Mongo supaya tidak bentrok
@EnableJpaRepositories(basePackages = "com.hanifa.peminjaman_service.repository", 
    considerNestedRepositories = true)
@EnableMongoRepositories(basePackages = "com.hanifa.peminjaman_service.repository")
@EntityScan(basePackages = "com.hanifa.peminjaman_service.model")
public class PeminjamanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeminjamanServiceApplication.class, args);
    }
}
