package com.hometest.controllers;

import com.hometest.controllers.data.Course;
import com.hometest.controllers.data.Student;
import com.hometest.respondHandling.ErrorResponse;
import com.hometest.respondHandling.SuccessResponse;
import com.hometest.service.CourseService;
import com.hometest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Retrieves all students.
     *
     * @return ResponseEntity containing a list of students (200 OK) or no content (204 No Content) if no students are found.
     */
    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> users = studentService.getAllStudent();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no components are found
        }
        return ResponseEntity.ok(users); // Returns 200 with the list of components
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param studentId The ID of the student to retrieve.
     * @return ResponseEntity containing the student (200 OK) or no content (204 No Content) if the student is not found.
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
     * Creates a new student.
     *
     * @param student The student object to create, passed in the request body.
     * @return ResponseEntity containing the created student (201 Created).
     */
    @PostMapping(value = "/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
    }

    /**
     * Updates an existing student.
     *
     * @param id      The ID of the student to update.
     * @param student The updated student object, passed in the request body.
     * @return ResponseEntity containing the updated student (200 OK).
     */
    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(id, student));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id The ID of the student to delete.
     * @return ResponseEntity with no content (204 No Content) upon successful deletion.
     */
    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(new SuccessResponse("Student deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    //---- CRUD Course ----

    /**
     * Retrieves all courses.
     *
     * @return ResponseEntity containing a list of courses (200 OK) or no content (204 No Content) if no courses are found.
     */
    @GetMapping("/course")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no components are found
        }
        return ResponseEntity.ok(courses); // Returns 200 with the list of components
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return ResponseEntity containing the course (200 OK) or no content (204 No Content) if the course is not found.
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
     * Creates a new course.
     *
     * @param course The course object to create, passed in the request body.
     * @return ResponseEntity containing the created course (201 Created).
     */
    @PostMapping(value = "/course")
    public ResponseEntity<Course> createCourse(@RequestBody Course course){
        Course newCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
    }

    /**
     * Updates an existing course.
     *
     * @param courseId The ID of the course to update.
     * @param course   The updated course object, passed in the request body.
     * @return ResponseEntity containing the updated course (200 OK).
     */
    @PutMapping("/course/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody Course course) {
        try {
            return ResponseEntity.ok(courseService.updateCourse(courseId, course));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    /**
     * Deletes a course by its ID.
     *
     * @param courseId The ID of the course to delete.
     * @return ResponseEntity with no content (204 No Content) upon successful deletion.
     */
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.ok(new SuccessResponse("Course deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
