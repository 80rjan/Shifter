package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.learning.domain.Enrollment;
import com.shifterwebapp.shifter.assessment.domain.enums.QuizAttemptStatus;
import com.shifterwebapp.shifter.learning.domain.UserSkillSnapshot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Attempt number cannot be null")
    @Column(nullable = false)
    private Integer attemptNumber;

    @NotNull(message = "Started at cannot be null")
    @Column(nullable = false)
    private LocalDateTime startedAt;

    @NotNull(message = "Completed at cannot be null")
    @Column(nullable = false)
    private LocalDateTime completedAt;

    @NotNull(message = "Status cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizAttemptStatus status;

    @NotNull(message = "Score cannot be null")
    @Column(nullable = false)
    private Integer score;

    @NotNull(message = "Total points cannot be null")
    @Column(nullable = false)
    private Integer totalPoints;

    @NotNull(message = "Earned points cannot be null")
    @Column(nullable = false)
    private Integer earnedPoints;

    @Column(nullable = false)
    private boolean passed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @OneToMany(mappedBy = "quizAttempt", cascade = {}, fetch = FetchType.LAZY)
    private List<UserSkillSnapshot> skillSnapshots;

    @Builder.Default
    @OneToMany(mappedBy = "quizAttempt", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<QuizAttemptAnswer> quizAttemptAnswers = new ArrayList<>();
}
