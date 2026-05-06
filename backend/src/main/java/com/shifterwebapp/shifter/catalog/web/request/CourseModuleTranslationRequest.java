package com.shifterwebapp.shifter.catalog.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseModuleTranslateReq {

    private Long id;

    private String title;

    private List<CourseLectureTranslateReq> courseLectures;

}
