package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.catalog.domain.CourseModule;
import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
import com.shifterwebapp.shifter.assessment.domain.enums.QuizType;
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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Type cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizType type;

    @NotNull(message = "Passing score cannot be null")
    @Column(nullable = false)
    private Integer passingScore;

    @Builder.Default
    @NotNull(message = "Randomized cannot be null")
    @Column(nullable = false)
    private boolean randomized = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizTranslation> translations = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_module_id", nullable = true)
    private CourseModule courseModule;                      // TODO: unique index for (module, quiz) and (version, quiz) to ensure one quiz per module/version

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_version_id", nullable = true)
    private CourseVersion courseVersion;

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizAttempt> quizAttempts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizQuestion> questions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
