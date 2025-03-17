package com.hometest;

import com.hometest.model.Course;
import com.hometest.model.Student;
import com.hometest.respondHandling.errorMessages.CourseFullException;
import com.hometest.respondHandling.errorMessages.StudentAlreadyEnrolledException;
import com.hometest.service.CourseService;
import com.hometest.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class StudentCourseServiceTest {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

    private Student student1;
    private Student student2;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        student1 = studentService.getStudentByName("Leah Goldberg");
        student2 = studentService.getStudentByName("Michael Jackson");
        course1 = courseService.getCourseByName("Mathematical logic and Set theory");
        course2 = courseService.getCourseByName("Linear algebra");
    }

    @Test
    void courseShouldNotExceedMaxStudents() {
        for (int i = 0; i < 30; i++) {
            Student newStudent = new Student("Student" + i, "student" + i + "@mail.com");
            studentService.createStudent(newStudent);
            studentService.enrollStudent(newStudent.getId(), course2.getId());
        }

        Student extraStudent = new Student("Extra", "extra@mail.com");
        studentService.createStudent(extraStudent);
        Exception exception = assertThrows(CourseFullException.class, () -> studentService.enrollStudent(extraStudent.getId(), course2.getId()));
    }

    @Test
    void studentShouldNotRegisterForMoreThanTwoCourses() {
        Course course3 = new Course("Spring Boot", "Master Spring Boot",LocalDate.now());
        Course course4 = new Course("Data Structures", "Learn DS in depth",LocalDate.now());
        courseService.createCourse(course3);
        courseService.createCourse(course4);
        studentService.enrollStudent(student1.getId(), course3.getId());
        studentService.enrollStudent(student1.getId(), course4.getId());

        Course extraCourse = new Course("Algorithms", "Learn algorithms effectively",LocalDate.now());
        courseService.createCourse(extraCourse);
        Exception exception = assertThrows(StudentAlreadyEnrolledException.class, () -> studentService.enrollStudent(student1.getId(), extraCourse.getId()),
                "A student should not register for more than two courses");
    }
}

