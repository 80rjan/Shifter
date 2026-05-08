package com.shifterwebapp.shifter.catalog.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseModuleTranslationRequest {

    private Long id;

    private String title;

    private List<CourseLectureTranslationRequest> courseLectures;

}
