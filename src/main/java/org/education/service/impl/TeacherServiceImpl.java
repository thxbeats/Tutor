package org.education.service.impl;

import lombok.AllArgsConstructor;
import org.education.dto.TeacherDTO;
import org.education.mapper.TeacherMapper;
import org.education.model.Teacher;
import org.education.repository.TeacherRepository;
import org.education.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = TeacherMapper.mapToTeacher(teacherDTO);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return TeacherMapper.mapToTeacherDTO(savedTeacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
}
