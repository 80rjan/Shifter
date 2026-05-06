package com.shifterwebapp.shifter.catalog.web.response;

import com.shifterwebapp.shifter.catalog.domain.enums.CourseLectureContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLectureDtoLearn {

    private Long id;

    private String title;

    private String description;

    private Integer durationMinutes;

    private Integer position;

    private String contentText;

    private String contentFileName;

    private CourseLectureContentType courseLectureContentType;

    private LectureProgressDto userCourseProgress;
}
