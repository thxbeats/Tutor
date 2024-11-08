package org.education.controllers.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.education.dto.UserDTO;
import org.education.model.User;
import org.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/step1")
    public String step1(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String conf_password,
                        RedirectAttributes redirectAttrs)
    {
        // Сохраняем email и пароль в сессии для передачи на второй шаг
        if(checkPassword(password,conf_password)){
            redirectAttrs.addFlashAttribute("email", email);
            redirectAttrs.addFlashAttribute("password", password);
            return "redirect:/register/step2";
        }
        else {
            System.out.println("ERROR! PASsWORDS DON'T MATCH!");

            return "redirect:/login";
        }
    }

    @GetMapping("/register/step2")
    public String step2(@ModelAttribute("email") String email,
                        @ModelAttribute("password") String password,
                        Model model) {
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        return "./auth/registration_step2";
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserDTO()); // добавляем пустой объект для формы
        return "./auth/loginform"; // возвращает шаблон register.html
    }


    boolean checkPassword(String password, String conf_password){
        return conf_password.equals(password);
    }
}