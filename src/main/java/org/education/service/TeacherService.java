package org.education.service;

import org.education.dto.TeacherDTO;
import org.education.model.Teacher;

public interface TeacherService {
    TeacherDTO createTeacher(TeacherDTO teacherDTO);
}
