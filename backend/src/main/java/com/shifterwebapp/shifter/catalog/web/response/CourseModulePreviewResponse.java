package com.shifterwebapp.shifter.catalog.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModuleDtoPreview {

    private String title;

    private Integer position;

    private List<CourseLectureDtoPreview> courseLectures;
}

