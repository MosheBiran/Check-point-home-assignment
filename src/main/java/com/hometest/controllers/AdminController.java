package com.hometest.controllers;

import com.hometest.controllers.data.Course;
import com.hometest.controllers.data.Student;
import com.hometest.service.CourseService;
import com.hometest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    //---- Crud Student
    /**
     * This function is meant for testing to see all the pre-loaded users
     * @return Return a list of all the users entities.
     */
    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAllStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated user: " + authentication.getName() + ", Authorities: " + authentication.getAuthorities()); // Add this line
        // ...
        List<Student> users = studentService.getAllStudent();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no components are found
        }
        return ResponseEntity.ok(users); // Returns 200 with the list of components
    }

    /**
     * This function is meant for testing to see all the pre-loaded users
     * @return Return a list of all the users entities.
     */
    @GetMapping(value = "/student/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.noContent().build(); // Returns 204 if no components are found
        }
        return ResponseEntity.ok(student); // Returns 200 with the list of components
    }

    /**
     *
     * @param student
     * @return
     */
    @PostMapping(value = "/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }


    //---- Crud Course
    /**
     * This function is meant for testing to see all the pre-loaded users
     * @return Return a list of all the users entities.
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no components are found
        }
        return ResponseEntity.ok(courses); // Returns 200 with the list of components
    }

    /**
     * This function is meant for testing to see all the pre-loaded users
     * @return Return a list of all the users entities.
     */
    @GetMapping(value = "/course/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCoursesById(courseId);
        if (course == null) {
            return ResponseEntity.noContent().build(); // Returns 204 if no components are found
        }
        return ResponseEntity.ok(course); // Returns 200 with the list of components
    }
    /**
     *
     * @param course
     * @return
     */
    @PostMapping(value = "/course")
    public ResponseEntity<Course> createCourse(@RequestBody Course course){
        Course newCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
    }

    /**
     *
     * @param courseId
     * @param course
     * @return
     */
    @PutMapping("/course/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, course));
    }

    /**
     *
     * @param courseId
     * @return
     */
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }


}
