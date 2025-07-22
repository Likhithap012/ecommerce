package com.learning.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFluxSecurity
public class GatewayApplication {

	public static void main(String[] args) {
		System.out.println("Starting Gateway Application...");
		SpringApplication.run(GatewayApplication.class, args);
	}
}
