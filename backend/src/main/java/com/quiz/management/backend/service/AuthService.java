package com.quiz.management.backend.service;


import com.quiz.management.backend.dto.RegisterRequest;
import com.quiz.management.backend.dto.RegisterResponse;
import com.quiz.management.backend.entity.Users;
import com.quiz.management.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        // Hash the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        Users user = Users.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase())
                .password(hashedPassword)
                .role(Users.Role.STUDENT)
                .build();

        Users savedUsers = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUsers.getId())
                .name(savedUsers.getName())
                .email(savedUsers.getEmail())
                .role(savedUsers.getRole().name())
                .message("Registration successful")
                .build();
    }
}
