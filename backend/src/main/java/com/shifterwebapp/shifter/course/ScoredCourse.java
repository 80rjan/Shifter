package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.course.course.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoredCourse {
    private Course course;
    private Integer score;
}
