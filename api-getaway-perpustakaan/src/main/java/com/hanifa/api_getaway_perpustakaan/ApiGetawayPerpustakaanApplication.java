package com.hanifa.api_getaway_perpustakaan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGetawayPerpustakaanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGetawayPerpustakaanApplication.class, args);
	}

}
