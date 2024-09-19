package org.education.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class Main {

    @GetMapping("/hello")
    public String hello(Model model) {
        // Передача сообщения в модель
        model.addAttribute("message", "Welcome to the Platform");
        // Возврат имени шаблона hello.html
        return "hello";
    }
}
