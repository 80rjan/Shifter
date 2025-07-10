package com.shifterwebapp.shifter.enrollment;

import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {

    private Long id;

    private EnrollmentStatus enrollmentStatus;

    private Integer percentCompleted;

    private Date date;
}

