package com.shifterwebapp.shifter.course.dto;

import com.shifterwebapp.shifter.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoPreviewEnrolled extends CourseDtoPreview {

    private Integer lecturesFinishedCount;

    private Integer rating;

    private Boolean isFinished;
}

