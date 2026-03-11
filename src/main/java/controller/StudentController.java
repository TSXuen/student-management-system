package com.example.student_management_system.controller;

import com.example.student_management_system.entity.Student;
import com.example.student_management_system.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String viewHomePage(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        model.addAttribute("students", studentService.searchStudents(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalStudents", studentService.getTotalStudents());

        return "index";
    }

    @GetMapping("/showNewStudentForm")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult result) {

        if (student.getId() == null) {
            if (studentService.isStudentIdDuplicateForCreate(student.getStudentId())) {
                result.rejectValue("studentId", "error.student", "Student ID already exists");
            }
        } else {
            if (studentService.isStudentIdDuplicateForUpdate(student.getId(), student.getStudentId())) {
                result.rejectValue("studentId", "error.student", "Student ID already exists");
            }
        }

        if (result.hasErrors()) {
            if (student.getId() == null) {
                return "add-student";
            } else {
                return "edit-student";
            }
        }

        studentService.saveStudent(student);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit-student";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/";
    }
}