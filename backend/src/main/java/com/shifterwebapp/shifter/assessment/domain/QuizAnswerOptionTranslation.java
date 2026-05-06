package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.shared.domain.Language;
import com.shifterwebapp.shifter.assessment.domain.QuizAnswerOption;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class QuizAnswerOptionTranslate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Answer text cannot be null")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String answerText;

    @NotNull(message = "Explanation cannot be null")
    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;                              // todo: add unique index for pair (language, quizansweroption)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_answer_option_id", nullable = false)
    private QuizAnswerOption answerOption;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
