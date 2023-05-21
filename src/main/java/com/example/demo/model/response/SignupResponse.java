package com.example.demo.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponse {
    private String userId;
    private String link;
    private String message;
}