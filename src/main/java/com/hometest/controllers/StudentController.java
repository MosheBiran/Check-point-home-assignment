package com.hometest.controllers;

import com.hometest.controllers.data.Course;
import com.hometest.controllers.data.User;
import com.hometest.respondHandling.ErrorResponse;
import com.hometest.respondHandling.SuccessResponse;
import com.hometest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * StudentController handles student-specific operations like course registration,
 * dropping courses, and retrieving enrolled courses.
 * It provides REST endpoints for students.
 */
@RestController
@RequestMapping("/api/student") // Base path for all student endpoints
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){this.studentService = studentService;}
    /**
     * Registers a student for a course.
     *
     * @param courseId The ID of the course to register for.
     * @return ResponseEntity with 200 OK on successful registration, or 400 Bad Request if the user is invalid or not a student.
     */
    @PostMapping(value = "/course/{courseId}")
    public ResponseEntity<?> RegisterCourse(@PathVariable long courseId) {
        // Retrieve the authenticated user from the SecurityContextHolder
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the user is valid and is a student
        if (user == null || user.getRole() != User.Role.STUDENT) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid user or not a student"));
        }

        try {
            studentService.enrollStudent(user.getId(), courseId);
            return ResponseEntity.ok().body(new SuccessResponse("Successfully enrolled in course"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Drops a student from a course.
     *
     * @param courseId The ID of the course to drop.
     * @return ResponseEntity with 200 OK on successful drop, or 400 Bad Request if the user is invalid or not a student.
     */
    @DeleteMapping(value = "/course/{courseId}")
    public ResponseEntity<?> dropCourse(@PathVariable long courseId) {
        // Retrieve the authenticated user from the SecurityContextHolder
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the user is valid and is a student
        if (user == null || user.getRole() != User.Role.STUDENT) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid user or not a student")); // Return 400 with an error message
        }

        // Drop the student from the course using the student service
        try {
            studentService.dropCourse(user.getId(), courseId);
            return ResponseEntity.ok().body(new SuccessResponse("Successfully dropped course"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves all courses that a student is enrolled in.
     *
     * @return ResponseEntity containing a list of enrolled courses (200 OK) or no content (204 No Content) if no courses are found.
     */
    @GetMapping("/course")
    public ResponseEntity<List<Course>> getUserCourses() {
        // Retrieve the authenticated user from the SecurityContextHolder
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get the set of courses the student is enrolled in using the student service
        Set<Course> courses = studentService.getStudentCourse(user.getId());

        // Check if the student is enrolled in any courses
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no courses are found
        }

        // Convert the set of courses to a list and return it with 200 OK
        return ResponseEntity.ok(new ArrayList<>(courses));
    }
}