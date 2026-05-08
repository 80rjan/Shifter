package com.shifterwebapp.shifter.catalog.web.request;

import com.shifterwebapp.shifter.catalog.domain.enums.CourseLectureContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLectureTranslationRequest {

    private Long id;

    private String title;

    private String description;

    private Integer durationMinutes;

    private String contentText;

    private String contentFileName;

    private CourseLectureContentType courseLectureContentType;
}
