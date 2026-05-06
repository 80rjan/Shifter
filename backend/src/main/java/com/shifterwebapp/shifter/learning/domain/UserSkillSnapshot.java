package com.shifterwebapp.shifter.learning.domain;

import com.shifterwebapp.shifter.learning.domain.Enrollment;
import com.shifterwebapp.shifter.assessment.domain.QuizAttempt;
import com.shifterwebapp.shifter.shared.domain.enums.SkillProficiency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a user's proficiency in a specific skill.
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// TODO: add indexes
public class UserSkillSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Proficiency cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillProficiency proficiencyAtTime;

    @Builder.Default
    @NotNull(message = "Proficiency score cannot be null")
    @Column(nullable = false)
    private Integer proficiencyScoreAtTime = 0;

    @NotNull(message = "New proficiency cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillProficiency newProficiency;

    @Builder.Default
    @NotNull(message = "New proficiency score cannot be null")
    @Column(nullable = false)
    private Integer newProficiencyScore = 0;

    // TODO: think about gaining skills in multiple ways (quiz, course completion) and with that adding snapshot type enum.

    // A option was to add confidance to the user skill. That way i multiply the proficiency score with the confidance to get a more accurate representation of the user's skill level.
    // But for now i will keep it simple and just use the proficiency score as is. This is so i dont stimulate retrials and just focus on general progress.
    // Maybe in the future i can add a confidance field to the user skill and use that to calculate a more accurate proficiency score.

    @Column(nullable = false)
    private LocalDateTime createdAt;

    //---------MAPPINGS---------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_attempt_id", nullable = false)
    private QuizAttempt quizAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_skill_id", nullable = false)
    private UserSkill userSkill;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
