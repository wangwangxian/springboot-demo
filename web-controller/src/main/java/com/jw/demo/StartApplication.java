package com.jw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"classpath:spring-context.xml"})
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class,args);
    }


}
