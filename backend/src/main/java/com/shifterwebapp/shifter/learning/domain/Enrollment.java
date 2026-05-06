package com.shifterwebapp.shifter.learning.domain;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
import com.shifterwebapp.shifter.learning.domain.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.learning.domain.enums.EnrollmentType;
import com.shifterwebapp.shifter.commerce.domain.OrderDetails;
import com.shifterwebapp.shifter.assessment.domain.QuizAttempt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// TODO: indexes
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentType enrollmentType;

    @Column(nullable = false)
    private boolean onTrial;                           // true if user is on trial period

    @Column(nullable = false)
    private LocalDate enrollmentDate;                  // date enrolled

    private LocalDate purchaseDate;                    // date purchased

    private LocalDate activationDate;                  // date activated

    private LocalDate completionDate;                  // date completed

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    //---------MAPPINGS---------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_version_id", nullable = false)
    private CourseVersion courseVersion;

    @OneToOne(mappedBy = "enrollment", fetch = FetchType.EAGER)
    private OrderDetails orderDetails;

    @Builder.Default
    @OneToMany(mappedBy = "enrollment", cascade = {}, fetch = FetchType.LAZY)
    private List<UserSkill> userSkills = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "enrollment", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private Review review;

    @OneToOne(mappedBy = "enrollment", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private Certificate certificate;

    @Builder.Default
    @OneToMany(mappedBy = "enrollment", cascade = {})
    private List<UserSkillSnapshot> userSkillSnapshots = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "enrollment", cascade = {}, fetch = FetchType.LAZY)
    private List<QuizAttempt> quizAttempts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "enrollment", cascade = {CascadeType.REMOVE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<LectureProgress> lectureProgresses = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.enrollmentDate = LocalDate.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

