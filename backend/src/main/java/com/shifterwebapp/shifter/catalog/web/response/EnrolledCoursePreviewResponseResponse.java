package com.shifterwebapp.shifter.catalog.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrolledCoursePreviewResponseResponse extends CoursePreviewResponse {

    private Integer lecturesFinishedCount;

    private Integer rating;

    private Boolean isFinished;
}

