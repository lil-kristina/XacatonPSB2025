package com.psb.education_platform.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_answers")
@Data
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "python_code", columnDefinition = "TEXT")
    private String pythonCode;

    @Column(name = "code_explanation", columnDefinition = "TEXT")
    private String codeExplanation;

    @Column(name = "file_link")
    private String fileLink;

    @Column(name = "answer_type")
    private String answerType = "code";

    @Column(name = "lesson_id")
    private Long lessonId = 1L;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        submissionDate = LocalDateTime.now();
    }
}