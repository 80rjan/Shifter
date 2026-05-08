package com.shifterwebapp.shifter.catalog.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseLearningResponse {

    private Long id;

    private String titleShort;

    private String title;

    private Integer rating;

    private Boolean isFinished;

    private List<CourseModuleLearningResponse> courseContents;
}
