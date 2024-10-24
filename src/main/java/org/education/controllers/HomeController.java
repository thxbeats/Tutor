package org.education.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class HomeController {

    @GetMapping("/")
    public String hello(Model model, Locale locale) {
        // Получаем текущего пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // логин пользователя
        System.out.println("Текущая локаль: " + locale);
        // Добавляем сообщение с логином
        model.addAttribute("message", "Welcome to the Platform, " + username);
        return "hello";
    }

    @GetMapping("/welcome")
    public String welcomePage(@RequestParam(name = "msg", required = false, defaultValue = "Welcome to the Turoring Platform") String msg, Model model) {
        model.addAttribute("message", msg);
        return "hello";
    }
}