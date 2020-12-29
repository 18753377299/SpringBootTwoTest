package com.example;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*审计功能*/
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//@SpringBootApplication
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class SpringBootTwoJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTwoJpaApplication.class, args);
    }
}
