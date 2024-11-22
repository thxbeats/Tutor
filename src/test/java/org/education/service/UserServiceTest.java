package org.education.service;

import org.education.config.PasswordConfig;
import org.education.config.TestSecurityConfig;
import org.education.dto.UserDTO;
import org.education.exceptions.UsernameAlreadyInUseException;
import org.education.model.Role;
import org.education.model.User;
import org.education.exceptions.EmailAlreadyInUseException;
import org.education.repository.RoleRepository;
import org.education.repository.UserRepository;
import org.education.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import(TestSecurityConfig.class) // Подключаем конфигурацию для тестов
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordConfig passwordConfig;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerNewUser_EmailAlreadyInUse_ShouldThrowException() {
        // Arrange
        String email = "test@example.com";
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User())); // Мокаем поведение

        // Act & Assert
        assertThrows(EmailAlreadyInUseException.class, () -> userServiceImpl.registerNewUser(userDTO));
    }

    @Test
    void registerNewUser_UsernameAlreadyInUse_ShouldThrowException(){
        String username = "failMe";
        UserDTO userDto = new UserDTO();
        userDto.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        assertThrows(UsernameAlreadyInUseException.class, () -> userServiceImpl.registerNewUser(userDto));
    }

    @Test
    void registerNewUser_Success_ShouldReturnUser() {
        // Arrange
        String email = "unique@example.com";
        String username = "uniqueUser";
        String password = "plainPassword";

        UserDTO userDTO = new UserDTO();
        Role role = new Role();
        role.setName("TEACHER");
        userDTO.setEmail(email);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setRole("TEACHER");
        userDTO.setFirstName("firstName");
        userDTO.setLastName("lastName");

        // Ожидаемый хешированный пароль
        String hashedPassword = "hashedPassword";
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(hashedPassword); // Хешированный пароль
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEnabled(true);
        user.setRoles(Set.of(role)); // Устанавливаем роль

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty()); // Email уникален
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty()); // Username уникален
        when(passwordConfig.hashPassword(anyString())).thenReturn(hashedPassword); // Мокаем хеширование
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role)); // Роль найдена
        when(userRepository.save(any(User.class))).thenReturn(user); // Мокаем сохранение пользователя

        // Act
        User result = userServiceImpl.registerNewUser(userDTO);

        // Assert
        assertNotNull(result); // Ожидаем, что результат не null
        assertEquals(email, result.getEmail()); // Проверяем email
        assertEquals(username, result.getUsername()); // Проверяем username
        assertEquals(userDTO.getFirstName(), result.getFirstName()); // Проверяем имя
        assertEquals(userDTO.getLastName(), result.getLastName()); // Проверяем фамилию
        assertTrue(result.isEnabled()); // Проверяем, что пользователь включен
        assertTrue(result.getRoles().contains(role)); // Проверяем, что роль назначена
        assertEquals("hashedPassword", result.getPassword()); // Проверяем, что пароль был хеширован
        verify(passwordConfig).hashPassword(password); // Проверяем, что метод hashPassword был вызван
        verify(userRepository).save(argThat(userArg ->
                userArg.getEmail().equals(email) &&
                        userArg.getUsername().equals(username) &&
                        userArg.getPassword().equals(hashedPassword))); // Проверяем, что save был вызван с правильным объектом
    }

}
