package org.education.repository;

import org.education.model.LessonRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRequestRepository extends JpaRepository<LessonRequest, Long> {
}
