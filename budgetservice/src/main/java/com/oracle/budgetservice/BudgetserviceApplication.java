package com.oracle.budgetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.oracle.proxy")
@ComponentScan(basePackages = "com.oracle.budgetservice")
public class BudgetserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetserviceApplication.class, args);
    }
}