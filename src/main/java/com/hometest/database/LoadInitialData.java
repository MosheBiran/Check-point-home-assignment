package com.hometest.database;

import com.hometest.controllers.data.Admin;
import com.hometest.controllers.data.Course;
import com.hometest.controllers.data.Student;
import com.hometest.controllers.data.User;
import com.hometest.database.repository.CourseRepository;
import com.hometest.database.repository.UserRepository;
import com.hometest.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class LoadInitialData {
    private static final Logger log = LoggerFactory.getLogger(LoadInitialData.class);
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean("initUsersData")
    InitializingBean initUserData() {
        return () -> {
            log.info("Initializing predefined Admin.");
            String password = passwordEncoder.encode("1234");
            userRepository.save(new Admin("admin","a45@walla.com", password));
            Student moshe = new Student("moshe biran","mos@walla.com");
            userRepository.save(moshe);
            userRepository.save(new Student("Knafa the cat","miaomiao@walla.com"));
            Course ml = new Course("Mathematical logic and Set theory");
            courseRepository.save(ml);
            courseRepository.save(new Course("Linear algebra"));

            studentService.enrollStudent(moshe.getId(),ml.getId());
            log.info("Finished to initialize predefined users.");
        };
    }
}
