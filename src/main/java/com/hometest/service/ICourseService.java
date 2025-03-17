package com.hometest.service;

import com.hometest.model.Course;

public interface ICourseService {
    Course createCourse(Course course);
    Course updateCourse(Long courseId ,Course course);
    void deleteCourse(Long courseId);
}
