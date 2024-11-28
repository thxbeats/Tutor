package org.education.service;

import org.education.dto.UserDTO;
import org.education.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerNewUser(UserDTO userDTO);
    User findByUsername(String username);
}
