package org.education.controllers.auth;

import org.education.dto.UserDTO;
import org.education.model.User;
import org.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
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
                        HttpSession session) {
        session.setAttribute("password", password);
        session.setAttribute("email", email);
        return "./auth/registration_step2";
    }

    private final RestTemplate restTemplate;


    @PostMapping("/register/step2")
    public String submitForm(@RequestParam String email,
                                             @RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam String middleName,
                                             @RequestParam Integer age,
                                             @RequestParam String role,
                                           HttpSession session,
                             Model model) {
        // URL на ваш эндпоинт /register
        String url = "https://localhost:8443/register";

        UserDTO formUserDTO = new UserDTO();
        String username = email.contains("@") ? email.split("@")[0] : email;
        formUserDTO.setUsername(username);
        // Остальные поля устанавливаем напрямую
        formUserDTO.setEmail(email);
        formUserDTO.setFirstName(firstName);
        formUserDTO.setLastName(lastName);
        formUserDTO.setRole(role);
        formUserDTO.setPassword(session.getAttribute("password").toString());
        System.out.println("UserDTO: " + formUserDTO);

        User newUser = userService.registerNewUser(formUserDTO);

        ResponseEntity<User> answer = new ResponseEntity<>(newUser, HttpStatus.CREATED);
        System.out.println(answer);

        // Добавляем данные в модель для передачи на страницу
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("middleName", middleName);
        model.addAttribute("age", age);
        model.addAttribute("role", role);
        model.addAttribute("email", email);

        return "./auth/registeredUser";
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