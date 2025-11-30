package com.psb.education_platform.service;

import com.psb.education_platform.entity.StudentAnswer;
import com.psb.education_platform.repository.StudentAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAnswerService {

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    public StudentAnswer saveStudentAnswer(String studentName, String pythonCode,
                                           String codeExplanation, String fileLink,
                                           String answerType, Long lessonId) {
        StudentAnswer answer = new StudentAnswer();
        answer.setStudentName(studentName);
        answer.setPythonCode(pythonCode);
        answer.setCodeExplanation(codeExplanation);
        answer.setFileLink(fileLink);
        answer.setAnswerType(answerType != null ? answerType : "code");
        answer.setLessonId(lessonId != null ? lessonId : 1L);

        return studentAnswerRepository.save(answer);
    }

    public java.util.List<StudentAnswer> getAllAnswers() {
        return studentAnswerRepository.findAll();
    }
}