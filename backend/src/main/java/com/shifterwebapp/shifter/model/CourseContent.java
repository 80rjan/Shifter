package com.shifterwebapp.shifter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CourseContent {
    private String title;
    private Integer position;
    private String contentURL;
    private ContentType contentType;
}

enum ContentType {
    VIDEO,
    TEXT,
    FILE,
    QUIZ
}
