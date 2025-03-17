package com.hometest.config;

import com.hometest.model.Admin;
import com.hometest.model.Course;
import com.hometest.model.Student;
import com.hometest.repository.CourseRepository;
import com.hometest.repository.UserRepository;
import com.hometest.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;


@Configuration
public class LoadInitialData {
    private static final Logger log = LoggerFactory.getLogger(LoadInitialData.class);
    private final StudentService studentService;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoadInitialData(StudentService studentService,UserRepository userRepository, CourseRepository courseRepository,PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @Bean("initUsersData")
    InitializingBean initUserData() {
        return () -> {
            log.info("Initializing predefined Admin.");
            String password = passwordEncoder.encode("1234");
            userRepository.save(new Admin("admin","itBurnWhenIP@Yahoo.com", password));

            Student s1 = new Student("moshe biran","isTheBestChoice@walla.co.il");
            Student s2= new Student("Knafa the cat","mewMew@walla.co.il");
            Student s3 = new Student("Eli Kopter","FunnyBunny@Gmail.com");
            Student s4= new Student("Harry Potter","Expelliarmus@Hogwarts.uk");
            Student s5 = new Student("Michael Jackson","He_Hee@Rockroll.com");
            Student s6 = new Student("Leah Goldberg","Myong@Rockroll.com");
            log.info("Finish initialize Students");
            userRepository.save(s1);
            userRepository.save(s2);
            userRepository.save(s3);
            userRepository.save(s4);
            userRepository.save(s5);
            userRepository.save(s6);

            Course c1 = new Course("Mathematical logic and Set theory");
            Course c2 = new Course("Linear algebra");
            Course c3 = new Course("Data Structures: The Revenge of the Linked List",
                    "Dive into the world of arrays, stacks, queues, and trees—because pointers love making your life difficult.",
                    LocalDate.now());

            Course c4 = new Course("Cloud Computing: Where Your Files Go to Hide",
                    "Learn how to store, manage, and accidentally delete important data in the cloud.",
                    LocalDate.now());

            Course c5 = new Course("Cybersecurity: Hackers vs. Humans",
                    "Discover how to protect your passwords (or at least make them better than '123456').",
                    LocalDate.now());

            Course c6 = new Course("AI & Machine Learning: Teaching Robots to Steal Your Job",
                    "Train neural networks to do what you do, but with fewer coffee breaks.",
                    LocalDate.now());

            Course c7 = new Course("Full-Stack Mayhem: Surviving Java, JavaScript & Everything in Between",
                    "Master both frontend and backend development while debugging for hours with zero progress.",
                    LocalDate.now());

            courseRepository.save(c1);
            courseRepository.save(c2);
            courseRepository.save(c3);
            courseRepository.save(c4);
            courseRepository.save(c5);
            courseRepository.save(c6);
            courseRepository.save(c7);
            log.info("Finish initialize Courses");

            studentService.enrollStudent(s1.getId(), c7.getId());
            studentService.enrollStudent(s2.getId(), c3.getId());
            studentService.enrollStudent(s2.getId(), c4.getId());
            studentService.enrollStudent(s3.getId(), c5.getId());
            studentService.enrollStudent(s4.getId(), c7.getId());
            studentService.enrollStudent(s5.getId(), c3.getId());
            log.info("Finished Connect courses to Students");
        };
    }
}
