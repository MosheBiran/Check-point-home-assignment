package com.hometest.service;

import com.hometest.controllers.data.Course;
import com.hometest.database.repository.CourseRepository;
import com.hometest.respondHandling.errorMessages.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CourseService provides business logic for managing Course entities.
 * It implements the ICourseService interface and interacts with the CourseRepository.
 */
@Service
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;

    }
    /**
     * Creates a new course.
     *
     * @param course The Course object to be created.
     * @return The saved Course object.
     */
    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Updates an existing course with the provided details.
     *
     * @param courseId      The ID of the course to update.
     * @param courseDetails The Course object containing the updated details.
     * @return The updated Course object.
     * @throws RuntimeException if the course is not found.
     */
    @Override
    public Course updateCourse(Long courseId, Course courseDetails) {
        return courseRepository.findById(courseId).map(course -> {
            // Update course name if provided
            if (courseDetails.getName() != null) {
                course.setName(courseDetails.getName());
            }
            // Update course description if provided
            if (courseDetails.getDescription() != null) {
                course.setDescription(courseDetails.getDescription());
            }
            // Update max number of students if provided and valid
            // Only update if the new max number is greater than zero and not less than the current number of students
            if (courseDetails.getMaxNumberOfStudents() > 0 && course.getNumberOfStudents() <= courseDetails.getMaxNumberOfStudents()) {
                course.setMaxNumberOfStudents(courseDetails.getMaxNumberOfStudents());
            }
            return courseRepository.save(course);
        }).orElseThrow(() -> new CourseNotFoundException("Course not found: " + courseId.toString()));
    }

    /**
     * Deletes a course by its ID.
     *
     * @param courseId The ID of the course to delete.
     */
    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId.toString()));

        // Remove students before deleting
        course.getEnrolledStudents().forEach(student -> student.dropCourse(course));

        courseRepository.delete(course);
    }

    /**
     * Retrieves all courses.
     *
     * @return A list of all Course objects.
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return The Course object with the given ID.
     * @throws RuntimeException if the course is not found.
     */
    public Course getCoursesById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException("Course not found:" + courseId.toString()));
    }

    public Course getCourseByName(String Name) {
        return courseRepository.findByName(Name)
                .orElseThrow(() -> new CourseNotFoundException(Name));
    }
}