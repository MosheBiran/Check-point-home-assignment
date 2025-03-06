package com.hometest.respondHandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class SuccessResponse {
    private String message;


    public SuccessResponse(String message) {
        this.message = message;
    }

}
