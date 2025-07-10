package com.shifterwebapp.shifter.course;

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
