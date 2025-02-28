package com.hometest.service;

import com.hometest.controllers.data.Course;
import com.hometest.database.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long courseId, Course courseDetails) {
        return courseRepository.findById(courseId).map(course -> {
            if (courseDetails.getName() != null) {
                course.setName(courseDetails.getName());
            }
            if (courseDetails.getDescription() != null) {
                course.setDescription(courseDetails.getDescription());
            }
            // Update the number of student eligible to sign in to a course Only if the number is greater than zero, And it's not lower than the already assigned student
            if (courseDetails.getMaxNumberOfStudents() > 0 && course.getNumberOfStudents() < courseDetails.getMaxNumberOfStudents()) {
                course.setMaxNumberOfStudents(courseDetails.getMaxNumberOfStudents());
            }
            return courseRepository.save(course);
        }).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course getCoursesById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    }


}
