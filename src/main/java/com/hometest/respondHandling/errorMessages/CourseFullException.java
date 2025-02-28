package com.hometest.respondHandling.errorMessages;

public class CourseFullException extends RuntimeException {
    public CourseFullException(String message) {
        super(message);
    }
}