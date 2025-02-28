package com.hometest.respondHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class SuccessResponse {
    private String message;


    public SuccessResponse(String message) {
        this.message = message;
    }

}
