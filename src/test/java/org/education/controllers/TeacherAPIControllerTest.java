package org.education.controllers;

import org.education.service.TeacherService;
import org.education.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherAPIController.class)
public class TeacherAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    TeacherServiceImpl teacherServiceMock;

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = {"ADMIN"})
    public void testCreateTeacher() throws Exception {
        String jsonTeacher = "{\"firstName\": \"Владимир\", \"secondName\": \"Алексеев\", \"email\": \"vladimir40@email.com\"}";


        mockMvc.perform(post("/api/teachers")
                //.contentType(MediaType.APPLICATION_JSON)
                .content(jsonTeacher))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonTeacher));
    }

    @Test
    public void testCreateTeacherUnauthorized() throws Exception {
        String jsonTeacher = "{\"firstName\": \"Владимир\", \"secondName\": \"Алексеев\", \"email\": \"vladimir122@email.com\"}";

        mockMvc.perform(post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTeacher))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = {"ADMIN"})
    public void must_succeed() throws Exception {
        mockMvc.perform(post("/api/teachers/create"))
                .andExpect(status().isOk());
    }
}
