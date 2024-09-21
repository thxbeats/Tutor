package org.education.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teacher_subjects")
public class TeacherSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @Getter
    @Setter
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @Getter
    @Setter
    private Subject subject;

    @Getter
    @Setter
    private double price;
}
