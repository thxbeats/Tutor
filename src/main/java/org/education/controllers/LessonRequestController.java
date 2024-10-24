package org.education.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.education.model.LessonRequest;
import org.education.model.Subject;
import org.education.repository.LessonRequestRepository;
import org.education.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LessonRequestController {

    @Autowired
    private LessonRequestRepository lessonRequestRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // Отображение формы и списка заявок
    @GetMapping("/lessonRequest")
    public String showForm(Model model, HttpServletRequest request) {
        // Получаем куки
        String savedName = null;
        String savedSubject = null;
        String savedDate = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("name")) {
                    savedName = cookie.getValue();
                } else if (cookie.getName().equals("subject")) {
                    savedSubject = cookie.getValue();
                } else if (cookie.getName().equals("date")) {
                    savedDate = cookie.getValue();
                }
            }
        }

        // Добавляем значения из куки в модель
        model.addAttribute("savedName", savedName);
        model.addAttribute("savedSubject", savedSubject);
        model.addAttribute("savedDate", savedDate);

        List<LessonRequest> requests = lessonRequestRepository.findAll(); // Получаем все заявки
        model.addAttribute("requests", requests); // Передаем заявки в модель для отображения в таблице

        return "lessonRequest";
    }

    // Обработка отправленной заявки
    @PostMapping("/submitRequest")
    public String submitRequest(@RequestParam String name,
                                @RequestParam String subject,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                HttpServletResponse response,
                                Model model) {

        List<String> errors = new ArrayList<>();
        List<LessonRequest> requests = lessonRequestRepository.findAll(); // Получаем все заявки
        model.addAttribute("requests", requests);

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

        // Сохраняем данные формы в куки
        Cookie nameCookie = new Cookie("name", name);
        Cookie subjectCookie = new Cookie("subject", subject);
        Cookie dateCookie = new Cookie("date", date.toString());

        // Устанавливаем срок жизни куки (например, 7 дней)
        int maxAge = 7 * 24 * 60 * 60;
        nameCookie.setMaxAge(maxAge);
        subjectCookie.setMaxAge(maxAge);
        dateCookie.setMaxAge(maxAge);

        // Добавляем куки в ответ
        response.addCookie(nameCookie);
        response.addCookie(subjectCookie);
        response.addCookie(dateCookie);

        // Сохраняем заявку в БД
        LessonRequest lessonRequest = new LessonRequest(name, subject, date);
        lessonRequestRepository.save(lessonRequest);

        // После успешной обработки перенаправляем на страницу с результатом
        model.addAttribute("name", lessonRequest.getName());
        model.addAttribute("date", lessonRequest.getDate());
        model.addAttribute("subject", lessonRequest.getSubject());

        return "result"; // Переход на страницу с результатом
    }
}
