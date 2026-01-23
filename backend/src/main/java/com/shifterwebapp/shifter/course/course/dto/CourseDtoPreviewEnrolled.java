package com.shifterwebapp.shifter.course.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoPreviewEnrolled extends CourseDtoPreview {

    private Integer lecturesFinishedCount;

    private Integer rating;

    private Boolean isFinished;
}

