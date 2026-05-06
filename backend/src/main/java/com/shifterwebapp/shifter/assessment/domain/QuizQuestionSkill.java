package com.shifterwebapp.shifter.assessment.domain;

import com.shifterwebapp.shifter.catalog.domain.Skill;
import com.shifterwebapp.shifter.shared.domain.enums.SkillProficiency;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class QuizQuestionSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private SkillProficiency proficiency;

    private Integer weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id", nullable = false)
    private QuizQuestion question;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
