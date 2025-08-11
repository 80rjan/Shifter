package com.shifterwebapp.shifter.enrollment;

import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {

    private EnrollmentStatus enrollmentStatus;

    private Date date;

    private Long courseId;

    private List<UserCourseProgressDto> userCourseProgressList;
}

