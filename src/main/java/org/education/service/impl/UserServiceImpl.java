package org.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.education.dto.UserDTO;
import org.education.model.Role;
import org.education.model.User;
import org.education.repository.RoleRepository;
import org.education.repository.UserRepository;
import org.education.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.education.config.PasswordConfig;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordConfig passwordConfig;;

    @Override
    @Transactional
    public User registerNewUser(UserDTO userDTO) {

    String rawPassword = userDTO.getPassword();

        String encodedPassword = passwordConfig.hashPassword(rawPassword);
        userDTO.setPassword(encodedPassword);

    if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
        throw new RuntimeException("Username is already taken");
    }
    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
        throw new RuntimeException("Email is already in use");
    }

    // Создание нового пользователя
    User user = new User();
    user.setUsername(userDTO.getUsername());
    user.setPassword(encodedPassword); // Используйте закодированный пароль
    user.setEmail(userDTO.getEmail());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setEnabled(true);

    // Присвоение ролей
    Set<Role> roles = new HashSet<>();
    Optional<Role> userRole = roleRepository.findByName(userDTO.getRole());
    userRole.ifPresent(roles::add);
    if (roles.isEmpty()) {
        throw new RuntimeException("Role not found");
    }
    user.setRoles(roles);

    return userRepository.save(user);
}

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
