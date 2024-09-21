package org.education.controllers;

import org.education.model.Teacher;
import org.education.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/teachers")
    public String getTeachers(Model model) {
    List<Teacher> teachers = teacherRepository.findAll();
    System.out.println("Teachers: " + teachers); // Лог для отладки
    model.addAttribute("teachers", teachers);
    return "teachers";
    }


}