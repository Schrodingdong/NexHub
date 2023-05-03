package com.schrodingdong.resourcemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
public class ResourceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceManagerApplication.class, args);
	}

}
