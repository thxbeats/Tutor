package org.education.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "Welcome to the Platform");
        return "hello";
    }

    @GetMapping("/welcome")
    public String welcomePage(@RequestParam(name = "msg", required = false, defaultValue = "Welcome to the hahaha") String msg, Model model) {
    model.addAttribute("message", msg);
    return "hello";
}


}