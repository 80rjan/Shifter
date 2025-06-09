package com.shifterwebapp.shifter.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrollment_seq")
    @SequenceGenerator(name = "enrollment_seq", sequenceName = "enrollment_sequence", allocationSize = 1)
    private Integer id;

    private EnrollmentStatus enrollmentStatus;

    private Integer percentCompleted;

    private Date date;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(mappedBy = "enrollment")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

enum EnrollmentStatus {
    PENDING,
    ACTIVE,
    COMPLETED
}