package org.education.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class LessonRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subject;
    private LocalDate date;

    public LessonRequest(String name, String subject, LocalDate date) {
        this.name = name;
        this.subject = subject;
        this.date = date;
    }

    public LessonRequest() {
    }
}
