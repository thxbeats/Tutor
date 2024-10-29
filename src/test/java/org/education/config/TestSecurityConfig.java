package org.education.config;

import org.education.service.CustomUserDetailsService;
import org.education.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@Profile("test") // Делаем конфигурацию активной только для профиля test
@Import(CustomUserDetailsService.class)
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Отключаем CSRF для упрощения тестирования
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/teachers/getTeachers").permitAll()
                         .requestMatchers("/api/teachers/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/teachers/**").permitAll()
                        .requestMatchers("/api/student/**").hasAnyRole("STUDENT", "ADMIN")
                .anyRequest().authenticated() // Разрешаем все запросы в тестовой конфигурации
            )
            .httpBasic(withDefaults()); // Включаем HTTP Basic для тестирования

        return http.build();
    }

    @Bean
    public UserRepository userRepository() {
        return null;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Определяем пользователей с ролями
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123")) // Шифруем пароль
                .roles("ADMIN")
                .build();

        UserDetails teacher = User.withUsername("teacher")
                .password(passwordEncoder().encode("teacher123")) // Шифруем пароль
                .roles("TEACHER")
                .build();

        UserDetails student = User.withUsername("student")
                .password(passwordEncoder().encode("student123")) // Шифруем пароль
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, teacher, student);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Используем NoOpPasswordEncoder для тестирования
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()); // Используем UserDetailsService
        return authenticationManagerBuilder.build();
    }
}