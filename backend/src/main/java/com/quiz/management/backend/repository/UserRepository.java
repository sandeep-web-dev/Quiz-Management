package com.quiz.management.backend.repository;

import com.quiz.management.backend.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Please provide a valid email") String email);

    Optional<Users> findByEmail(String email);

}
