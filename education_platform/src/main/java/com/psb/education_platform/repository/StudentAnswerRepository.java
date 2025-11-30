package com.psb.education_platform.repository;

import com.psb.education_platform.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByStudentName(String studentName);

    List<StudentAnswer> findByLessonId(Long lessonId);
}
