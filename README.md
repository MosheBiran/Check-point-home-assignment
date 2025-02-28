Here’s the complete README content for you to copy:

# Student Course Registration System

## Overview
This is a Java Spring Boot web application for managing student course registrations. The system includes authentication, CRUD operations, and session handling using an H2 in-memory database.

## Technologies Used

- Java 17

- Spring Boot 3

- Spring Security

- MySQL (Persistent Storage)

- H2 Database (In-Memory for Testing)

- Maven

- Postman (API Testing) 
### API documentation is available through the .iml file 
- (Through Postman -> Right click On collection -> View documentation)
## General Information

- This is a Spring Boot and MySQL web application for student course registration. The system includes authentication for both admins and students, CRUD operations for courses and users, and session handling.

- Supported Features:

- Spring Boot for backend development

- MySQL as the primary database

- H2 Database support for testing, accessible via browser (http://localhost:8080/h2-console)

- RESTful API endpoints for managing courses and users
- Authentication and session handling
- Postman collection for API testing

## Project Structure

### `com.hometest.database`
- **LoadInitialData.java** – Loads initial data into the database.
- **repository/** – Contains repository interfaces for database operations.

### `com.hometest.respondHandling`
- **GlobalExceptionHandler.java** – Handles global exceptions.
- **SuccessResponse.java** – Defines a standardized success response format.
- **ErrorResponse.java** – Defines a standardized error response format.

### `com.hometest.security`
- **SecurityConfig.java** – Configures authentication and authorization.
- **JwtAuthenticationFilter.java** – Implements JWT-based authentication.

### `com.hometest.service`
- **StudentService.java**, **IStudentService.java** – Manages student-related business logic.
- **CourseService.java**, **ICourseService.java** – Manages course-related business logic.
- **CustomUserDetailsService.java** – Integrates user authentication with Spring Security.

### `com.hometest.controllers`
- **AdminController.java** – Handles admin-related API requests.
- **StudentController.java** – Handles student-related API requests.

## Database Schema
The system uses an H2 in-memory database with the following tables:

### **Student Table**
| Column  | Type    | Description |
|---------|--------|-------------|
| id      | INT    | Primary Key |
| name    | STRING | Student Name |
| email   | STRING | Unique Email |

### **Course Table**
| Column  | Type    | Description |
|---------|--------|-------------|
| id      | INT    | Primary Key |
| title   | STRING | Course Title |
| credits | INT    | Credit Hours |

### **Enrollment Table**
| Column   | Type | Description |
|----------|------|-------------|
| id       | INT  | Primary Key |
| student_id | INT | Foreign Key (Student) |
| course_id  | INT | Foreign Key (Course) |


## Installation & Setup

1. Clone the repository:
```shell
git clone <repository_url>
cd student-course-registration
```


2. Configure MySQL database in application.properties:
```shell
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=root
spring.datasource.password=your_password
```
3. (Optional) Use H2 Database for testing:
```shell
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```
4. Access H2 Database console at: http://localhost:8080/h2-console

5. Run the application:
```shell
mvn spring-boot:run
```
