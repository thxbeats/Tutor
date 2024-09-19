package org.education.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class Main {

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "Welcome to the Platform");
        return "hello";
    }
}
