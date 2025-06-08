package com.shifterwebapp.shifter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Enrollment {
    private EnrollmentStatus enrollmentStatus;
    private Integer percentCompleted;
    private Date date;
}

enum EnrollmentStatus {
    PENDING,
    ACTIVE,
    COMPLETED
}