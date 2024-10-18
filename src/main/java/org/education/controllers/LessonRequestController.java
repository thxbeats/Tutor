package org.education.controllers;

import org.education.model.LessonRequest;
import org.education.model.Subject;
import org.education.repository.LessonRequestRepository;
import org.education.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LessonRequestController {

    @Autowired
    private LessonRequestRepository lessonRequestRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // Отображение формы и списка заявок
    @GetMapping("/lessonRequest")
    public String showForm(Model model) {
        List<LessonRequest> requests = lessonRequestRepository.findAll(); // Получаем все заявки
        model.addAttribute("requests", requests); // Передаем заявки в модель для отображения в таблице
        return "lessonRequest"; // Имя HTML страницы с формой и таблицей
    }

    // Обработка отправленной заявки
    @PostMapping("/submitRequest")
    public String submitRequest(@RequestParam String name,
                                @RequestParam String subject,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                Model model) {
        List<String> errors = new ArrayList<>();

        // Проверяем, существует ли предмет в базе данных
        Subject existingSubject = subjectRepository.findByName(subject);
        if (existingSubject == null) {
            errors.add("Предмет '" + subject + "' не существует.");
        }

        // Валидация данных формы
        if (name == null || name.length() < 2) {
            errors.add("Имя должно содержать не менее 2 символов.");
        }
        if (date == null) {
            errors.add("Дата не может быть пустой.");
        }

        // Если есть ошибки, возвращаем их на страницу с формой
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors); // Передаем список ошибок в модель
            return "lessonRequest"; // Возвращаемся на страницу с формой и отображаем ошибки
        }

        // Если ошибок нет, сохраняем заявку в БД
        LessonRequest lessonRequest = new LessonRequest(name, subject, date);
        System.out.println(lessonRequest); System.out.println(lessonRequest.getName());
        lessonRequestRepository.save(lessonRequest);
        model.addAttribute("name", lessonRequest.getName());
        model.addAttribute("date", lessonRequest.getDate());
        model.addAttribute("subject", lessonRequest.getSubject());
        // После успешной обработки перенаправляем на страницу с результатом
        return "result"; // Переход на страницу с результатом
    }
}
