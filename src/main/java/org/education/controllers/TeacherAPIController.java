package org.education.controllers;

import lombok.AllArgsConstructor;
import org.education.dto.TeacherDTO;
import org.education.model.Teacher;
import org.education.repository.TeacherRepository;
import org.education.service.TeacherService;
import org.education.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* TODO
Добавить Красивый ответ
Решить проблему с ID (если находит учителя с занятым ID  - ошибка)
*/
@RestController
@AllArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherAPIController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO savedTeacher = teacherService.createTeacher(teacherDTO);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<String> testPost(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getTeachers")
    public List<Teacher> getTeachers() {
        return teacherService.getAllTeachers();
    }
}