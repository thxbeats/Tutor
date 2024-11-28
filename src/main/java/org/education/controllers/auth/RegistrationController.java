package org.education.controllers.auth;

import org.education.dto.RegistrationDTO;
import org.education.dto.UserDTO;
import org.education.exceptions.EmailAlreadyInUseException;
import org.education.exceptions.PasswordsNotMatchingException;
import org.education.mapper.RegistrationMapper;
import org.education.model.User;
import org.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
    }

    @PostMapping("/register/step1")
    public String step1(@Valid @ModelAttribute RegistrationDTO userDTO, @NotNull BindingResult bindingResult, Model model,
                        @RequestParam String email,
                        Locale locale,
                        HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "./auth/loginform";
        }

        System.out.println("GOT FROM MODEL:" + userDTO);
        try {
            if(!checkPassword(userDTO.getPassword(), userDTO.getConfirmPassword())){
                throw new PasswordsNotMatchingException();
            };

            UserDTO formUserDTO = RegistrationMapper.toUserDto(userDTO);
            formUserDTO.setUsername(email);

            System.out.println("UserDTO (mapped): " + formUserDTO);

            User newUser = userService.registerNewUser(formUserDTO);

            ResponseEntity<User> answer = new ResponseEntity<>(newUser, HttpStatus.CREATED);
            System.out.println(answer);

            model.addAttribute("firstName", formUserDTO.getFirstName());
            model.addAttribute("lastName", formUserDTO.getLastName());
            model.addAttribute("role", formUserDTO.getRole());
            model.addAttribute("email", formUserDTO.getEmail());
            return "./auth/registeredUser";
        }
        catch (PasswordsNotMatchingException e){
            model.addAttribute("errorMessage", messageSource.getMessage(e.getErrorCode(), null, locale));
            System.out.println("Passwords do not match!");
            model.addAttribute("showRegistrationForm", true);
            return "./auth/loginform";

        }
        catch (EmailAlreadyInUseException e) {
            model.addAttribute("errorMessage", messageSource.getMessage(e.getErrorCode(), new Object[] {email}, locale));
            model.addAttribute("showRegistrationForm", true);
            System.out.println("EMAIL IN USE!");
            return "./auth/loginform";
        } catch (Exception e) {
            model.addAttribute("errorMessage", messageSource.getMessage("error.generic", null, locale));
            model.addAttribute("showRegistrationForm", true);
            System.out.println("ERROR!: \n" + e.getMessage());
            return "./auth/loginform";
        }
    }


    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model,
                                HttpSession session) {
        model.addAttribute("registrationDTO", new RegistrationDTO());
        if (error != null) {
            model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", session.getAttribute("logoutMessage"));
            session.removeAttribute("logoutMessage");
        }
        return "./auth/loginform"; // возвращает шаблон register.html
    }


    boolean checkPassword(String password, String conf_password) {
        return conf_password.equals(password);
    }
}