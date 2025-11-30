package com.psb.education_platform.controller;

import com.psb.education_platform.entity.StudentAnswer;
import com.psb.education_platform.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class FrontendController {

    @Autowired
    private StudentAnswerService studentAnswerService;

    // 📝 ОСНОВНОЙ ЭНДПОИНТ - сохранение ответа
    @PostMapping("/answers/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody Map<String, String> requestData) {
        try {
            System.out.println("📥 Получен запрос на сохранение ответа:");
            System.out.println("👤 Имя: " + requestData.get("studentName"));
            System.out.println("🐍 Код: " + requestData.get("pythonCode"));
            System.out.println("📝 Пояснение: " + requestData.get("codeExplanation"));
            System.out.println("📎 Файл: " + requestData.get("fileLink"));
            System.out.println("📁 Тип: " + requestData.get("answerType"));

            // 🔒 ВАЛИДАЦИЯ
            String studentName = requestData.get("studentName");
            String pythonCode = requestData.get("pythonCode");
            String fileLink = requestData.get("fileLink");
            String answerType = requestData.get("answerType");

            if (studentName == null || studentName.trim().isEmpty()) {
                return errorResponse("Имя студента обязательно");
            }

            // Проверяем что есть либо код, либо файл
            if ((pythonCode == null || pythonCode.trim().isEmpty()) &&
                    (fileLink == null || fileLink.trim().isEmpty())) {
                return errorResponse("Должен быть либо код Python, либо ссылка на файл");
            }

            // Сохраняем в базу данных
            StudentAnswer savedAnswer = studentAnswerService.saveStudentAnswer(
                    studentName,
                    pythonCode,
                    requestData.get("codeExplanation"),
                    fileLink,
                    answerType,
                    requestData.get("lessonId") != null ? Long.parseLong(requestData.get("lessonId")) : 1L
            );

            System.out.println("✅ Ответ сохранен в БД с ID: " + savedAnswer.getId());

            // Успешный ответ
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "✅ Ваш ответ успешно сохранен!");
            response.put("answerId", savedAnswer.getId());
            response.put("studentName", savedAnswer.getStudentName());
            response.put("answerType", savedAnswer.getAnswerType());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ Ошибка при сохранении: " + e.getMessage());
            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "❌ Ошибка сервера: " + e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // 🔧 Вспомогательный метод для ошибок
    private ResponseEntity<?> errorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", message);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    // 📊 Получить все ответы (для проверки)
    @GetMapping("/answers")
    public ResponseEntity<?> getAllAnswers() {
        try {
            java.util.List<StudentAnswer> answers = studentAnswerService.getAllAnswers();
            return ResponseEntity.ok(answers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    // 🏓 Проверка работы сервера
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("✅ Сервер работает! Время: " + java.time.LocalDateTime.now());
    }
}