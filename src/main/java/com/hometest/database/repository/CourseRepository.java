package com.hometest.database.repository;
import com.hometest.controllers.data.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}