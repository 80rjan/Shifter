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
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    private LocalDate purchaseDate;                  // date purchased

    private LocalDate activationDate;                  // date activated

    private LocalDate completionDate;                  // date completed

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    // when enrollment is saved, payment is saved. Delete payment from db if payment=null
    @JoinColumn(name = "payment_id")                // references payment.id
    private Payment payment;

    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "course_version_id", nullable = false)                 // references courseVersion.id
    private CourseVersion courseVersion;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourseProgress> userCourseProgress;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    // references user.id, no null to ensure enrollment always linked to a user
    private User user;
}

