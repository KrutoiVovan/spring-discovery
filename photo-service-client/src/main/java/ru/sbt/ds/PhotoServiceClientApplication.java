package ru.sbt.ds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class PhotoServiceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoServiceClientApplication.class, args);
	}
}
