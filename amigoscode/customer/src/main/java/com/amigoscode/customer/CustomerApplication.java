package com.amigoscode.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
    scanBasePackages = {
        "com.amigoscode.customer",
        "com.amigoscode.amqp"
    }
)
@EnableFeignClients(basePackages = "com.amigoscode.clients")

//@PropertySources({
//    @PropertySource(value= "classpath:clients-default.properties", ignoreResourceNotFound = true ),
//    @PropertySource(value = "classpath:clients-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
//})

public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
    
}
