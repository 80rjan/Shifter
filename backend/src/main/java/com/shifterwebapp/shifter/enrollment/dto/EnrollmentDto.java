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

    private LocalDate purchasedAt;
    private LocalDate activatedAt;
    private LocalDate completedAt;

    private Long courseVersionId;

    private List<UserCourseProgressDto> userCourseProgress;
}

