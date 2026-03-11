package com.example.student_management_system.repository;

import com.example.student_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFullNameContainingIgnoreCase(String fullName);

    boolean existsByStudentId(String studentId);

    Optional<Student> findByStudentId(String studentId);
}