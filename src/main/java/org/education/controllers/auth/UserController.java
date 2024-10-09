package org.education.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.education.dto.UserDTO;
import org.education.model.User;
import org.education.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        User newUser = userService.registerNewUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
