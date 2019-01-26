package de.grzb.verleihservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.*;

@SpringBootApplication
@EnableDiscoveryClient
public class VerleihServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerleihServiceApplication.class, args);
	}

}

