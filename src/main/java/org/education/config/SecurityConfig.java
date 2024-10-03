package org.education.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Определяем пользователей с ролями
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}admin123")  // {noop} указывает, что пароль не зашифрован
                .roles("ADMIN")
                .build();

        UserDetails teacher = User
                .withUsername("teacher")
                .password("{noop}teacher123")
                .roles("TEACHER")
                .build();

        UserDetails student = User
                .withUsername("student")
                .password("{noop}student123")
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, teacher, student);
    }

    // Существующий метод конфигурации безопасности
   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable) // Postman HotFix
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/teachers/**").hasAnyRole("TEACHER", "ADMIN")  // Только учитель и админ могут работать с учителями
                    .requestMatchers("/api/student/**").hasAnyRole("STUDENT", "ADMIN")  // Только ученик имеет доступ
                    .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
            )
            .formLogin(withDefaults())
            .httpBasic(withDefaults());

    return http.build();
}
}