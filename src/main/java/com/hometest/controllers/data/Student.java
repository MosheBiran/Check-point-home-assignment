package com.hometest.controllers.data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("STUDENT")

public class Student extends User {

    @Column(unique = true)
    private String specialKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Student_Course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonIgnoreProperties("enrolledStudents") // 🔹 Hide students when serializing courses
    private Set<Course> courses = new HashSet<>();;


    public Student(String name, String email) {
        super(name,email);
        generateSpecialKey();
    }

    @PrePersist
    public void generateSpecialKey() {
        if (this.specialKey == null) {
            this.specialKey = UUID.randomUUID().toString(); // Generate a random key
        }
    }


    public boolean addEnrolledCourse(Course course) {
        return !courses.contains(course) && courses.add(course);
    }

    @JsonIgnore
    public int getNumberOfEnrolledCourse() {
        return courses.size();
    }

}