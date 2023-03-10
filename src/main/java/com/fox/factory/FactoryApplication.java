package com.fox.factory;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;



@EntityScan("com.fox.factory*")
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class FactoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(FactoryApplication.class, args);
    }
}
