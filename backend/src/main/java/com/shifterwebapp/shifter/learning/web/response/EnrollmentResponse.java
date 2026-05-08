package com.shifterwebapp.shifter.learning.web.response;

import com.shifterwebapp.shifter.learning.domain.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponse {

    private EnrollmentStatus enrollmentStatus;

    private LocalDate purchaseDate;
    private LocalDate activationDate;
    private LocalDate completionDate;

    private Long courseVersionId;

    private Integer percentCompleted;
}

