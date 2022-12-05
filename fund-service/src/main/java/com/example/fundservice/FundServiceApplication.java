package com.example.fundservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FundServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundServiceApplication.class, args);
    }

}
