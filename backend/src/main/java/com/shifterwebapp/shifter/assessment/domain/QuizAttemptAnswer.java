package com.shifterwebapp.shifter.assessment.domain;

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
public class QuizAttemptAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Correct bool flag cannot be null")
    @Column(nullable = false)
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id", nullable = false)
    private QuizQuestion question;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "quiz_attempt_answer_selected_options",
            joinColumns = @JoinColumn(name = "quiz_attempt_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_answer_option_id")
    )
    private List<QuizAnswerOption> selectedAnswers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_attempt_id", nullable = false)
    private QuizAttempt quizAttempt;
}
