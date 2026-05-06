package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.shared.domain.Language;
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
public class QuizTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Description cannot be null")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;                              // todo: add unique index for pair (language, quiz)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
