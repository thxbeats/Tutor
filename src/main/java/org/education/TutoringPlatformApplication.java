package org.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TutoringPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutoringPlatformApplication.class, args);
    }
}