package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.assessment.domain.QuizAnswerOptionTranslate;
import jakarta.persistence.*;
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
public class QuizAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean correct;

    @OneToMany(mappedBy = "answerOption", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizAnswerOptionTranslate> translations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id", nullable = false)
    private QuizQuestion question;

    @Builder.Default
    @ManyToMany(mappedBy = "selectedAnswers", fetch = FetchType.LAZY)
    private List<QuizAttemptAnswer> quizAttemptAnswers = new ArrayList<>();
}
