package org.education.controllers;

import lombok.AllArgsConstructor;
import org.education.dto.TeacherDTO;
import org.education.model.Teacher;
import org.education.repository.TeacherRepository;
import org.education.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherAPIController {

    private TeacherService teacherService;
    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO){
        TeacherDTO savedTeacher = teacherService.createTeacher(teacherDTO);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

}