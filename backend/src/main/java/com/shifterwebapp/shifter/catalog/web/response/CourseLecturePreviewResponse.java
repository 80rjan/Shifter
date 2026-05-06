package com.shifterwebapp.shifter.catalog.web.response;

import com.shifterwebapp.shifter.catalog.domain.enums.CourseLectureContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLectureDtoPreview {

    private String title;

    private String description;

    private Integer durationMinutes;

    private Integer position;

    private CourseLectureContentType courseLectureContentType;
}
