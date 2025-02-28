package com.hometest.respondHandling;

import com.hometest.respondHandling.errorMessages.CourseFullException;
import com.hometest.respondHandling.errorMessages.CourseNotFoundException;
import com.hometest.respondHandling.errorMessages.StudentAlreadyEnrolledException;
import com.hometest.respondHandling.errorMessages.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Student Not Found", ex.getMessage(), request.getDescription(false).substring(4));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Course Not Found", ex.getMessage(), request.getDescription(false).substring(4));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<ErrorResponse> handleStudentAlreadyEnrolledException(StudentAlreadyEnrolledException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Student Already Enrolled", ex.getMessage(), request.getDescription(false).substring(4));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CourseFullException.class)
    public ResponseEntity<ErrorResponse> handleCourseFullException(CourseFullException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Course Full", ex.getMessage(), request.getDescription(false).substring(4));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // Add more exception handlers as needed
}
