package org.education.controllers;


import jakarta.servlet.http.HttpSession;
import org.education.model.User;
import org.education.repository.SubjectRepository;
import org.education.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyAccountController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/lk")
    public String showCabinet(HttpSession session, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roles = authentication.getAuthorities().toString();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("USER: " +user.toString());
        System.out.println("name " +user.getFirstName());
        System.out.println("name2 " +user.getLastName());
        System.out.println("email " +user.getEmail());
        System.out.println("roles " +user.getRoles());
        System.out.println("created at " +user.getCreatedAt());

        model.addAttribute("email", user.getEmail());
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("full-name", user.getFirstName() + user.getLastName());
        model.addAttribute("created-at", user.getCreatedAt());


        return "lk";
    }

}
