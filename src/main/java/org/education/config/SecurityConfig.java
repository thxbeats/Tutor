package org.education.config;

import org.education.service.CustomUserDetailsService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /* Определяем пользователей с ролями
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
*/
    // Существующий метод конфигурации безопасности
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MessageSource messageSource,  LocaleResolver localeResolver) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/teachers/getTeachers").permitAll()
                        // .requestMatchers("/api/teachers/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/teachers/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/student/**").hasAnyRole("STUDENT", "ADMIN")
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/auth.css").permitAll()
                        .requestMatchers("/login", "/login?lang=ru", "/login?lang=en").permitAll()
                        .requestMatchers("/register", "/register/*").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        //  .defaultSuccessUrl("/", true)
                        .failureHandler((request, response, exception) -> {
                            String errorMessage;
                            Locale locale = localeResolver.resolveLocale(request); // Определяем текущую локаль
                            Throwable cause = exception.getCause();
                            if (exception instanceof BadCredentialsException) {
                                errorMessage = messageSource.getMessage("error.bad_credentials", null, locale);
                            } else if (cause instanceof DisabledException) {
                                errorMessage = messageSource.getMessage("error.account_disabled", null, locale);
                                System.out.println("HEY, I AM HERE!");
                            }
                            else {
                                errorMessage = messageSource.getMessage("error.generic", null, locale);
                            }
                            request.getSession().setAttribute("errorMessage", errorMessage);
                            response.sendRedirect("/login?error=true");
                        }))
                .httpBasic(withDefaults())
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            Locale locale = localeResolver.resolveLocale(request);
                            String logoutMessage = messageSource.getMessage("logout.success", null, locale);
                            SecurityContextHolder.clearContext(); // Очищаем SecurityContext явно
                            System.out.println("Authentication after logout: " + SecurityContextHolder.getContext().getAuthentication());

                            request.getSession().setAttribute("logoutMessage", logoutMessage);
                            response.sendRedirect("/login?logout=true");

                        })
                        .permitAll()
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );


        return http.build();
    }

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;

    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
        return authenticationManagerBuilder.build();
    }
}