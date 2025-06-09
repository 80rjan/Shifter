package com.shifterwebapp.shifter.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {

    private EnrollmentStatus enrollmentStatus;

    private Integer percentCompleted;

    private Date date;

    private PaymentDto payment;

    private ReviewDto review;

    private CourseDto course;
}

enum EnrollmentStatus {
    PENDING,
    ACTIVE,
    COMPLETED
}
