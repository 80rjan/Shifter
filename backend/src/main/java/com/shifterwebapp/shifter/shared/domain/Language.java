package com.shifterwebapp.shifter.shared.domain;

import com.shifterwebapp.shifter.catalog.domain.*;
import com.shifterwebapp.shifter.collection.domain.bundle.BundleTranslation;
import com.shifterwebapp.shifter.catalog.domain.CourseModuleTranslation;
import com.shifterwebapp.shifter.collection.domain.learningpath.LearningPathTranslation;
import com.shifterwebapp.shifter.assessment.domain.QuizAnswerOptionTranslation;
import com.shifterwebapp.shifter.assessment.domain.QuizQuestionTranslation;
import com.shifterwebapp.shifter.assessment.domain.QuizTranslation;
import com.shifterwebapp.shifter.shared.domain.enums.LanguageCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageCode languageCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nativeName;

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<SkillTranslation> skillTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<TopicTranslation> topicTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseTranslation> courseTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseModuleTranslation> courseModuleTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseLectureTranslation> courseLectureTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<LearningPathTranslation> learningPathTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<BundleTranslation> bundleTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizTranslation> quizTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizQuestionTranslation> quizQuestionTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizAnswerOptionTranslation> quizAnswerOptionsTranslations = new ArrayList<>();
}
