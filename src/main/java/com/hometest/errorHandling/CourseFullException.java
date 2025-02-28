package com.hometest.errorHandling;

public class CourseFullException extends RuntimeException {
    public CourseFullException(String message) {
        super(message);
    }
}