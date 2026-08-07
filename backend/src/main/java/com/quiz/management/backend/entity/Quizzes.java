package com.quiz.management.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "quizzes", indexes = {
        @Index(name = "idx_quiz_updated_at", columnList = "updated_at")
})
@EntityListeners(AuditingEntityListener.class)
public class Quizzes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(nullable = false, length = 50)
    private String difficulty;          // e.g. EASY, MEDIUM, HARD

    private Integer duration;           // in minutes

    @Column(name = "passing_score")
    private Integer passingScore;

    @Column(name = "max_attempts")
    private Integer maxAttempts;

    @Column(nullable = false, length = 20)
    private String status;              // e.g. ACTIVE, INACTIVE, DRAFT

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
