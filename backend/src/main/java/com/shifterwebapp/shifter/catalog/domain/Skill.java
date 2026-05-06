package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.catalog.domain.Course;
import com.shifterwebapp.shifter.assessment.domain.QuizQuestionSkill;
import com.shifterwebapp.shifter.learning.domain.UserSkill;
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
// TODO: add indexes
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Slug cannot be null")
    @Column(nullable = false, unique = true)    // TODO: unique in index
    private String slug;

    @NotNull(message = "Show in radar cannot be null")
    @Column(nullable = false)
    private Boolean showInRadar;

    @Builder.Default
    @ManyToMany(mappedBy = "skills", cascade = {}, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "skill", cascade = {}, fetch = FetchType.LAZY)
    private List<UserSkill> userSkills = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "skill", cascade = {}, fetch = FetchType.LAZY)
    private List<QuizQuestionSkill> quizQuestionSkills = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "skill", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)     // TODO: fetch type?
    private List<SkillTranslate> translations = new ArrayList<>();
}
