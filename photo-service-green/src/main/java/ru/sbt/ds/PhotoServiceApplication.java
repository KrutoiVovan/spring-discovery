package ru.sbt.ds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;

@EnableEurekaClient
@SpringBootApplication
@PropertySource(value = "applicationGreen.properties")
public class PhotoServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhotoServiceApplication.class, args);
	}
}
