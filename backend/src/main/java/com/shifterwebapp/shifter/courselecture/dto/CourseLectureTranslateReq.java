package com.shifterwebapp.shifter.courselecture.dto;

import com.shifterwebapp.shifter.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLectureTranslateReq {

    private Long id;

    private String title;

    private String description;

    private Integer durationMinutes;

    private String contentText;

    private String contentFileName;

    private ContentType contentType;
}
