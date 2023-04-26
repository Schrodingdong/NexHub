package com.nexhub.databasemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DatabaseManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseManagerApplication.class, args);
	}

}
