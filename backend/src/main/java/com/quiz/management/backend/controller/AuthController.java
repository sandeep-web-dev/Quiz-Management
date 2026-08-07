package com.quiz.management.backend.controller;

import com.quiz.management.backend.dto.RegisterRequest;
import com.quiz.management.backend.dto.RegisterResponse;
import com.quiz.management.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){

        RegisterResponse response=authService.register(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
