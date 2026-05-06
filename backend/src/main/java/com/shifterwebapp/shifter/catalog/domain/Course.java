package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.collection.domain.bundle.Bundle;
import com.shifterwebapp.shifter.identity.domain.Expert;
import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.shared.domain.enums.Difficulty;
import com.shifterwebapp.shifter.collection.domain.learningpath.LearningPathCourse;
import com.shifterwebapp.shifter.commerce.domain.OrderDetails;
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
//todo: indexes
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String imageUrl;

    private String color;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<CoursePrice> prices = new ArrayList<>();

    @Transient
    public CoursePrice getActivePrice() {
        return prices.stream()
                .filter(CoursePrice::isActive)
                .findFirst()
                .orElse(null);
    }

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<RelatedCourse> relatedCourses;

    @OneToMany(mappedBy = "relatedToCourse", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<RelatedCourse> relatedToCourses;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Expert> experts;

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<CourseTranslation> translations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<LearningPathCourse> learningPathCourses = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Bundle> bundles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "course_topic",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private List<Topic> topics = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "course_skill",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "favoriteCourses", fetch = FetchType.LAZY)
    private List<User> favoritedByUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseActivityEvent> courseActivityEvents = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<CourseVersion> courseVersions = new ArrayList<>();
}

