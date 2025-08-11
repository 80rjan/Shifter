package com.shifterwebapp.shifter.enrollment;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.review.Review;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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

    private Date date;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)  // Persist ???? Orphan removal ????
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourseProgress> userCourseProgressList;
}

