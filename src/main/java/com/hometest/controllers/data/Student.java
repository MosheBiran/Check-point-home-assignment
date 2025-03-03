package com.hometest.controllers.data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
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
    private Set<Course> courses = new HashSet<>();

    public Student() {
        generateSpecialKey();
    }

    public Student(String name, String email) {
        super(name,email);
        generateSpecialKey();
    }

    @PrePersist
    private void generateSpecialKey() {
        if (this.specialKey == null) {
            this.specialKey = UUID.randomUUID().toString(); // Generate a random key
        }
    }


    public void addEnrolledCourse(Course course) {
         courses.add(course);
    }
    public void dropCourse(Course course) {
         courses.remove(course);
    }
    public boolean isEnrolledCourse(Course course) {
        return courses.contains(course);
    }

    @JsonIgnore
    public int getNumberOfEnrolledCourse() {
        return courses.size();
    }

}