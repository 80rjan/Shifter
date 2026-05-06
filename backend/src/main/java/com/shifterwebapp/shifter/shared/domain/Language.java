package com.shifterwebapp.shifter.shared.domain;

import com.shifterwebapp.shifter.collection.domain.bundle.BundleTranslate;
import com.shifterwebapp.shifter.catalog.domain.CourseLectureTranslate;
import com.shifterwebapp.shifter.catalog.domain.CourseModuleTranslate;
import com.shifterwebapp.shifter.catalog.domain.CourseTranslation;
import com.shifterwebapp.shifter.collection.domain.learningpath.LearningPathTranslate;
import com.shifterwebapp.shifter.assessment.domain.QuizAnswerOptionTranslate;
import com.shifterwebapp.shifter.assessment.domain.QuizQuestionTranslate;
import com.shifterwebapp.shifter.assessment.domain.QuizTranslation;
import com.shifterwebapp.shifter.catalog.domain.SkillTranslate;
import com.shifterwebapp.shifter.catalog.domain.TopicTranslate;
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
    private LanguageCode languageCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nativeName;

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<SkillTranslate> skillTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<TopicTranslate> topicTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseTranslation> courseTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseModuleTranslate> courseModuleTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseLectureTranslate> courseLectureTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<LearningPathTranslate> learningPathTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<BundleTranslate> bundleTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizTranslation> quizTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizQuestionTranslate> quizQuestionTranslations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "language", cascade = {}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<QuizAnswerOptionTranslate> quizAnswerOptionsTranslations = new ArrayList<>();
}
