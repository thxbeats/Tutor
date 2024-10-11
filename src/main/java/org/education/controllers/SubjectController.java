package org.education.controllers;

import org.education.model.Subject;
import org.education.repository.SubjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class SubjectController {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/subjects")
    public String getSubjects(Model model) {
    List<Subject> subjects = subjectRepository.findAll();
    model.addAttribute("subjects", subjects);
    return "subjects";
    }
}