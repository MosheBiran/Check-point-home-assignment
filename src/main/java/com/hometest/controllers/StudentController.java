package com.hometest.controllers;

import com.hometest.controllers.data.Course;
import com.hometest.controllers.data.Student;
import com.hometest.controllers.data.User;
import com.hometest.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping(value = "/course/{courseId}")
    public ResponseEntity<?> RegisterCourse(@PathVariable long courseId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || user.getRole() != User.Role.STUDENT) {
            return ResponseEntity.badRequest().body("Invalid user or not a student");
        }
        studentService.enrollStudent(user.getId(), courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/course/{courseId}")
    public ResponseEntity<?> dropCourse(@PathVariable long courseId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || user.getRole() != User.Role.STUDENT) {
            return ResponseEntity.badRequest().body("Invalid user or not a student");
        }
        studentService.dropCourse(user.getId(), courseId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/course")
    public ResponseEntity<List<Course>> getUserCourses() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Student student = studentService.getStudentById(user.getId());
        Set<Course> courses = student.getCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no components are found
        }
        return ResponseEntity.ok(new ArrayList<>(courses)); // Returns 200 with the list of components
    }
}
