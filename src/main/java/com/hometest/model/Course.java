package com.hometest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "course")
@NoArgsConstructor @Getter @Setter
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Course name is required")
    @Size(min = 2, max = 100, message = "Course name must be between 2 and 100 characters")
    private String name;
    private String description;
    private int maxNumberOfStudents = 30;
    private LocalDate startDate;


    public Course(String name){
        this.name = name;
    }
    public Course(String name, String description, LocalDate date){
        this.name = name;
        this.description = description;
        this.startDate = date;
    }

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"courses","specialKey"}) // 🔹 Hide courses when serializing students
    private Set<Student> enrolledStudents = new HashSet<>();

    @JsonIgnore
    public int getNumberOfStudents() {
        return enrolledStudents.size();
    }

    public void addEnrolledStudent(Student student) {
         enrolledStudents.add(student);
    }
    public void removeStudent(Student student) {
         enrolledStudents.remove(student);
    }

}
