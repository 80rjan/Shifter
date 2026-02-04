package com.shifterwebapp.shifter.enrollment;

import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.review.Review;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_enrollment_user_course_version",
                        columnNames = {"user_id", "course_version_id"}        // each user can have only one enrollment per course version
                )
        },
        indexes = {
                @Index(name = "idx_enrollment_user_id", columnList = "user_id"),
                @Index(name = "idx_enrollment_course_version_id", columnList = "course_version_id"),
                @Index(name = "idx_enrollment_enrollment_status", columnList = "enrollmentStatus"),
                @Index(name = "idx_enrollment_user_status", columnList = "user_id, enrollmentStatus"),
                @Index(name = "idx_enrollment_user_purchase", columnList = "user_id, purchaseDate DESC")
        }
)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    private LocalDate enrollmentDate;                  // date enrolled

    private LocalDate purchaseDate;                    // date purchased

    private LocalDate activationDate;                  // date activated

    private LocalDate completionDate;                  // date completed

    @OneToOne(mappedBy = "enrollment", orphanRemoval = true)
    // when enrollment is saved, payment is saved. Delete payment from db if payment=null
    private Payment payment;

    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "course_version_id", nullable = false)                 // references courseVersion.id
    private CourseVersion courseVersion;

    @OneToMany(mappedBy = "enrollment", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<UserCourseProgress> userCourseProgresses;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    // references user.id, no null to ensure enrollment always linked to a user
    private User user;

    @PrePersist
    protected void onCreate() {
        this.enrollmentDate = LocalDate.now();
    }
}

