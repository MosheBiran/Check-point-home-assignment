package com.hometest.service;

import com.hometest.controllers.data.*;
import com.hometest.database.repository.CourseRepository;
import com.hometest.database.repository.StudentRepository;
import com.hometest.database.repository.UserRepository;
import com.hometest.errorHandling.CourseFullException;
import com.hometest.errorHandling.StudentAlreadyEnrolledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Student createStudent(Student student) {
        // Generate Token
        student.generateSpecialKey();
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long studentId , Student studentDetails) {
        return studentRepository.findById(studentId).map(student -> {
            if (studentDetails.getName() != null) {
                student.setName(studentDetails.getName());
            }
            if (studentDetails.getEmail() != null) {
                student.setEmail(studentDetails.getEmail());
            }
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }


    @Transactional
    public void enrollStudent(Long studentId, Long courseId) {
        Student student = (Student) studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        Course course  = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        if(course.getNumberOfStudents() == course.getMaxNumberOfStudents()){
            throw new CourseFullException(String.format("The course %s Has reached its full capacity",course.getName()));
        }
        if(student.isEnrolledCourse(course)){
            throw new StudentAlreadyEnrolledException("Student is already enrolled to this course");
        }
        if(student.getNumberOfEnrolledCourse() == 2){
            throw new StudentAlreadyEnrolledException("Student is already enrolled to the max number of courses");
        }
        course.addEnrolledStudent(student);
        student.addEnrolledCourse(course);
        courseRepository.save(course);
        studentRepository.save(student);
    }

    @Override
    public void dropCourse(Long studentId, Long courseId) {
        Student student = (Student) studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Course course  = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        student.dropCourse(course);
        course.removeStudent(student);
        courseRepository.save(course);
        studentRepository.save(student);
    }


    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));
    }

    @Override
    public Student getStudentByName(String studentName) {
        return studentRepository.findByName(studentName)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentName));
    }

    public Student findBySpecialKey(String specialKey) {
        return studentRepository.findBySpecialKey(specialKey).orElseThrow(() -> new RuntimeException("Student not found: " + specialKey));
    }



}
