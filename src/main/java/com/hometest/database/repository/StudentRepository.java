package com.hometest.database.repository;

import com.hometest.controllers.data.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByName(String username);
    Optional<Student> findBySpecialKey(String specialKey);
}
