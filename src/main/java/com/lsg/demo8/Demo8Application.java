package com.lsg.demo8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.lsg.demo8.repository")
public class Demo8Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo8Application.class, args);
    }

}
