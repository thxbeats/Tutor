package org.education.controllers;


import jakarta.servlet.http.HttpSession;
import org.education.model.Role;
import org.education.model.User;
import org.education.repository.SubjectRepository;
import org.education.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MyAccountController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/lk")
    public String showCabinet(HttpSession session, Model model,  Locale locale){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String roles = authentication.getAuthorities().toString();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("USER: " +user.toString());
        System.out.println("name " +user.getFirstName());
        System.out.println("name2 " +user.getLastName());
        System.out.println("email " +user.getEmail());
        System.out.println("roles " +user.getRoles());
        System.out.println("created at " +user.getCreatedAt());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        LocalDate date = user.getCreatedAt().toLocalDate();
        String parsed_date = date.format(formatter);

        Set<Role> roles = user.getRoles();
        System.out.println("ROLES ARE: " + roles);
        String formattedRoles = formatRoles(roles, messageSource, locale);

        System.out.println("LOCALIZED ROLES ARE: " + formattedRoles);

        model.addAttribute("email", user.getEmail());
        model.addAttribute("localizedRoles", formattedRoles);
        model.addAttribute("full_name", String.format("%s %s", user.getFirstName(), user.getLastName()));
        model.addAttribute("created_at", parsed_date);


        return "lk";
    }

    public String formatRoles(Set<Role> roles, MessageSource messageSource, Locale locale) {
        return roles.stream()
                .map(role -> messageSource.getMessage("role." + role.getName(), null, locale)) // Перевод роли
                .collect(Collectors.joining(", ")); // Соединяем роли через запятую
    }

}
