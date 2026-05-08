package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.assessment.domain.enums.QuizQuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Position cannot be null")
    @Column(nullable = false)
    private Integer position;

    @NotNull(message = "Points cannot be null")
    @Column(nullable = false)
    private Integer points;

    @NotNull(message = "Type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuizQuestionType type;

    @Builder.Default
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizQuestionTranslation> translations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Builder.Default
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<QuizQuestionSkill> skills = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizAttemptAnswer> quizAttemptsAnswers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<QuizAnswerOption> answerOptions = new ArrayList<>();
}
