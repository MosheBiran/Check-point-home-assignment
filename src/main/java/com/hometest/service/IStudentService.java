package com.hometest.service;

import com.hometest.model.Course;
import com.hometest.model.Student;

import java.util.List;
import java.util.Set;

public interface IStudentService {
    //Admin - CRUD
    Student createStudent(Student student);
    Student updateStudent(Long studentId, Student student);
    void deleteStudent(Long studentId);

    //Student - Functions
    void enrollStudent(Long studentId, Long courseId);
    void dropCourse(Long studentId, Long courseId);

    //Data Access
    List<Student> getAllStudents();
    Student getStudentById(Long studentId);
    Student getStudentByName(String studentName);
    Set<Course> getStudentCourse(Long studentId);
    Student findBySpecialKey(String specialKey);
}
