package org.education.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.education.controllers.auth.UserController;
import org.education.dto.TeacherDTO;
import org.education.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@WebMvcTest(UserController.class) // Замените на ваш контроллер
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TeacherServiceImpl teacherServiceImpl; // Мокаем ваш сервис

    @BeforeEach
    public void setUp() {
        // Настройка мока, если нужно
    }

    @Test
public void testGetTeachers() throws Exception {
    // Подготовка данных
    List<Teacher> teachers = Arrays.asList(
            new Teacher(31L, "Иван", "Иванов", "teacher1@example.com"),
            new Teacher(32L, "Мария", "Петрова", "teacher2@example.com"),
            new Teacher(33L, "Алексей", "Сидоров", "teacher3@example.com"),
            new Teacher(34L, "Ольга", "Смирнова", "teacher4@example.com"),
            new Teacher(35L, "Дмитрий", "Кузнецов", "teacher5@example.com"),
            new Teacher(36L, "Анна", "Лебедева", "teacher6@example.com"),
            new Teacher(37L, "Сергей", "Попов", "teacher7@example.com"),
            new Teacher(38L, "Елена", "Михайлова", "teacher8@example.com"),
            new Teacher(39L, "Андрей", "Васильев", "teacher9@example.com"),
            new Teacher(40L, "Татьяна", "Николаева", "teacher10@example.com")
    );

    // Настройка мока для сервиса
    when(teacherServiceImpl.getAllTeachers()).thenReturn(teachers);

    // Выполнение запроса
    mockMvc.perform(get("/api/teachers/getTeachers")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName").value("Иван"))
            .andExpect(jsonPath("$[1].firstName").value("Мария"))
            .andExpect(jsonPath("$[2].firstName").value("Алексей"));
}
}*/