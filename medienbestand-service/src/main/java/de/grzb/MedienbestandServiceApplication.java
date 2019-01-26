package de.grzb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.*;

@SpringBootApplication
@EnableDiscoveryClient
public class MedienbestandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedienbestandServiceApplication.class, args);
    }
}
