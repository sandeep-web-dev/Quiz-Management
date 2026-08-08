package com.quiz.management.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String token;      // JWT token
    private String message;
}