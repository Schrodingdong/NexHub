package com.schrodingdong.resourcemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResourceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceManagerApplication.class, args);
	}

}
