package com.example.student_management_system.service;

import com.example.student_management_system.entity.Student;
import com.example.student_management_system.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return studentRepository.findAll();
        }
        return studentRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }

    public boolean isStudentIdDuplicateForCreate(String studentId) {
        return studentRepository.existsByStudentId(studentId);
    }

    public boolean isStudentIdDuplicateForUpdate(Long id, String studentId) {
        Optional<Student> existingStudent = studentRepository.findByStudentId(studentId);

        if (existingStudent.isEmpty()) {
            return false;
        }

        return !existingStudent.get().getId().equals(id);
    }
}