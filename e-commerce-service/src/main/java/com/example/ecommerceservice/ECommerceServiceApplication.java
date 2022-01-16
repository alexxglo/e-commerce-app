package com.example.ecommerceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class ECommerceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceServiceApplication.class, args);
    }

}
