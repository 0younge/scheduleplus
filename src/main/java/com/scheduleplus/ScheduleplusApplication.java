package com.scheduleplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScheduleplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleplusApplication.class, args);
    }

}
