package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.shared.domain.Language;
import com.shifterwebapp.shifter.assessment.domain.QuizQuestion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class QuizQuestionTranslate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String questionText;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String scenario;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;                              // todo: add unique index for pair (language, quizquestion)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id", nullable = false)
    private QuizQuestion question;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
