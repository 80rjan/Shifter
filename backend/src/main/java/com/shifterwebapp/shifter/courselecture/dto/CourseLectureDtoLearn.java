package com.shifterwebapp.shifter.courselecture.dto;

import com.shifterwebapp.shifter.enums.ContentType;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressDto;
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

    private ContentType contentType;

    private UserCourseProgressDto userCourseProgress;
}
