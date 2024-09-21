package org.education.mapper;

import org.education.dto.TeacherDTO;
import org.education.model.Teacher;

public class TeacherMapper {
    public static TeacherDTO mapToTeacherDTO(Teacher teacher){
        return new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getSecondName(),
                teacher.getEmail()
        );
    }

    public static Teacher mapToTeacher(TeacherDTO teacherDTO){
        return new Teacher(
                teacherDTO.getId(),
                teacherDTO.getFirstName(),
                teacherDTO.getSecondName(),
                teacherDTO.getEmail()
        );
    }
}
