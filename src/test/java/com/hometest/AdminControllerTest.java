package com.hometest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hometest.controllers.data.Admin;
import com.hometest.controllers.data.Course;
import com.hometest.controllers.data.Student;
import com.hometest.database.repository.UserRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    private Student student;
    private Course course;
    private Admin admin;
    private String encodedPassword;

    @PostConstruct
    public void setUp() {
        student = new Student("Test Student", "test@example.com");
        course = new Course("Test Course", "Test Description", LocalDate.now());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("1234");
        //Create admin user in database with the encoded password.
        //use for example your repository.
    }

    @Test
    @WithUserDetails(value = "admin")
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Student"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));
    }


}