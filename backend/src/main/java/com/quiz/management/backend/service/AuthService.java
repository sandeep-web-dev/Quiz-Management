package com.quiz.management.backend.service;


import com.quiz.management.backend.dto.LoginRequest;
import com.quiz.management.backend.dto.LoginResponse;
import com.quiz.management.backend.dto.RegisterRequest;
import com.quiz.management.backend.dto.RegisterResponse;
import com.quiz.management.backend.entity.Users;
import com.quiz.management.backend.repository.UserRepository;
import com.quiz.management.backend.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

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

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .message("Login successful")
                .build();
    }
}
