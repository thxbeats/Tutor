package org.education.controllers;

import org.education.config.TestSecurityConfig;
import org.education.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingController.class)
@Import(TestSecurityConfig.class) // Подключаем конфигурацию для тестов
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

     @MockBean
    private UserRepository userRepository; // Мокируем UserRepository


    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = {"ADMIN"}) // Добавляем аутентификацию
    public void testGreet_normal() throws Exception {
        mockMvc.perform(get("/greet"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
    }

    @Test
//    @WithMockUser(username = "admin", password = "admin123", roles = {"ADMIN"}) // Добавляем аутентификацию
    public void testGreet_401() throws Exception {
        mockMvc.perform(get("/greet"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = {"ADMIN"})
    public void testPost() throws Exception {
        String inputBody = "Test message";
        String expectedResponse = "Your message is: TEST MESSAGE";

        mockMvc.perform(post("/create")
                .content(inputBody) // Тело запроса
                .contentType(MediaType.TEXT_PLAIN)) // Указываем, что отправляем текст
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                /*.andExpect(content().string(expectedResponse))*/; // Проверяем содержимое ответа
    }





}