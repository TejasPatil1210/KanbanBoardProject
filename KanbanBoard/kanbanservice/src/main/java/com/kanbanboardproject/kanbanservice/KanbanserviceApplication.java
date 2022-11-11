package com.kanbanboardproject.kanbanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class KanbanserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanserviceApplication.class, args);
	}

}
