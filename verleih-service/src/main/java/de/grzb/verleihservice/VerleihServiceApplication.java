package de.grzb.verleihservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(considerNestedRepositories = true)
public class VerleihServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerleihServiceApplication.class, args);
	}

}

