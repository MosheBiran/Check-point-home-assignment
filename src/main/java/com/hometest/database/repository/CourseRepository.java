package com.hometest.database.repository;
import com.hometest.controllers.data.Course;
import com.hometest.controllers.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);

}