package org.education.controllers.auth;

import org.education.dto.UserDTO;
import org.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    // Метод для отображения страницы регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO()); // добавляем пустой объект для формы
        return "./auth/loginform"; // возвращает шаблон register.html
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserDTO()); // добавляем пустой объект для формы
        return "./auth/loginform"; // возвращает шаблон register.html
    }

    // Метод для обработки регистрации
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDTO userDTO) {
        // Логика сохранения пользователя
        userService.registerNewUser(userDTO);
        return "redirect:/login"; // перенаправление на страницу логина после регистрации
    }

}