package org.education.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Locale;

@Controller
public class HomeController {

    @GetMapping("/")
    public String hello(Model model, Locale locale, Principal principal) {
        // Получаем текущего пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // логин пользователя
        System.out.println("Текущая локаль: " + locale);
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        // Добавляем сообщение с логином
        model.addAttribute("message", "Welcome to the Platform, " + username);
        if (authentication != null) {
            System.out.println("Authenticated user: " + authentication.getName());
        } else {
            System.out.println("No authentication information available.");
        }
        return "hello";
    }

    @GetMapping("/welcome")
    public String welcomePage(@RequestParam(name = "msg", required = false, defaultValue = "Welcome to the Turoring Platform") String msg,Principal principal, Model model) {
        model.addAttribute("message", msg);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) authentication.getPrincipal();
        // model.addAttribute("user", user);
        return "hello";
    }
}