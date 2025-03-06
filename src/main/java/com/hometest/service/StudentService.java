package com.hometest.service;

import com.hometest.controllers.data.*;
import com.hometest.database.repository.CourseRepository;
import com.hometest.database.repository.StudentRepository;
import com.hometest.respondHandling.errorMessages.CourseFullException;
import com.hometest.respondHandling.errorMessages.CourseNotFoundException;
import com.hometest.respondHandling.errorMessages.StudentAlreadyEnrolledException;
import com.hometest.respondHandling.errorMessages.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * StudentService provides business logic for managing Student entities.
 * It implements the IStudentService interface and interacts with the StudentRepository,
 * UserRepository, and CourseRepository.
 */
@Service
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository){
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    /**
     * Creates a new student.
     *
     * @param student The Student object to be created.
     * @return The saved Student object.
     */
    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Updates an existing student with the provided details.
     *
     * @param studentId      The ID of the student to update.
     * @param studentDetails The Student object containing the updated details.
     * @return The updated Student object.
     * @throws RuntimeException if the student is not found.
     */
    @Override
    public Student updateStudent(Long studentId, Student studentDetails) {
        return studentRepository.findById(studentId).map(student -> {
            // Update student name if provided
            if (studentDetails.getName() != null) {
                student.setName(studentDetails.getName());
            }
            // Update student email if provided
            if (studentDetails.getEmail() != null) {
                student.setEmail(studentDetails.getEmail());
            }
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    /**
     * Deletes a student by their ID.
     *
     * @param studentId The ID of the student to delete.
     */
    @Override
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    /**
     * Enrolls a student in a course.
     *
     * @param studentId The ID of the student to enroll.
     * @param courseId  The ID of the course to enroll in.
     * @throws StudentNotFoundException if the student is not found.
     * @throws CourseNotFoundException  if the course is not found.
     * @throws CourseFullException      if the course is full.
     * @throws StudentAlreadyEnrolledException if the student is already enrolled in the course or has reached max enrolled courses.
     */
    @Transactional
    public void enrollStudent(Long studentId, Long courseId) {
        // Find the student by ID or throw an exception if not found
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId.toString()));

        // Find the course by ID or throw an exception if not found
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));

        // Check if the course is full
        if (course.getNumberOfStudents() == course.getMaxNumberOfStudents()) {
            throw new CourseFullException(String.format("The course %s Has reached its full capacity", course.getName()));
        }

        // Check if the student is already enrolled in the course
        if (student.isEnrolledCourse(course)) {
            throw new StudentAlreadyEnrolledException("Student is already enrolled to this course");
        }

        // Check if the student has reached the maximum number of enrolled courses
        if (student.getNumberOfEnrolledCourse() == 2) {
            throw new StudentAlreadyEnrolledException("Student is already enrolled to the max number of courses");
        }

        // Add the student to the course and vice versa
        course.addEnrolledStudent(student);
        student.addEnrolledCourse(course);

        // Save the updated course and student
        courseRepository.save(course);
        studentRepository.save(student);
    }

    /**
     * Drops a student from a course.
     *
     * @param studentId The ID of the student to drop.
     * @param courseId  The ID of the course to drop from.
     * @throws StudentNotFoundException if the student is not found.
     * @throws CourseNotFoundException  if the course is not found.
     */
    @Override
    public void dropCourse(Long studentId, Long courseId) {
        // Find the student by ID or throw an exception if not found
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId.toString()));

        // Find the course by ID or throw an exception if not found
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));

        // Remove the student from the course and vice versa
        student.dropCourse(course);
        course.removeStudent(student);

        // Save the updated course and student
        courseRepository.save(course);
        studentRepository.save(student);
    }

    /**
     * Retrieves all students.
     *
     * @return A list of all Student objects.
     */
    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param studentId The ID of the student to retrieve.
     * @return The Student object with the given ID.
     * @throws StudentNotFoundException if the student is not found.
     */
    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId.toString()));
    }

    /**
     * Retrieves a student by their name.
     *
     * @param studentName The name of the student to retrieve.
     * @return The Student object with the given name.
     * @throws StudentNotFoundException if the student is not found.
     */
    @Override
    public Student getStudentByName(String studentName) {
        return studentRepository.findByName(studentName)
                .orElseThrow(() -> new StudentNotFoundException(studentName));
    }

    /**
     * Retrieves the set of courses a student is enrolled in.
     *
     * @param studentId The ID of the student.
     * @return A set of Course objects the student is enrolled in.
     * @throws StudentNotFoundException if the student is not found.
     */
    public Set<Course> getStudentCourse(Long studentId) {
        // Find the student by ID or throw an exception if not found
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId.toString()));

        return student.getCourses();
    }

    /**
     * Finds a student by their special key.
     *
     * @param specialKey The special key of the student.
     * @return The Student object with the given special key.
     * @throws StudentNotFoundException if the student is not found.
     */
    public Optional<Student> findBySpecialKey(String specialKey) {
        return studentRepository.findBySpecialKey(specialKey);
    }
}