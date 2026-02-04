package com.shifterwebapp.shifter.enrollment.dto;

import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {

    private EnrollmentStatus enrollmentStatus;

    private LocalDate purchaseDate;
    private LocalDate activationDate;
    private LocalDate completionDate;

    private Long courseVersionId;

    private Integer percentCompleted;
}

