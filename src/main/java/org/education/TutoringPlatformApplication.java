package org.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "org.education")
public class TutoringPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutoringPlatformApplication.class, args);
    }
}