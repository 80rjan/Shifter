package com.shifterwebapp.shifter.courselecture;

import com.shifterwebapp.shifter.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLecturePreviewDto {

    private String title;

    private String description;

    private Integer durationMinutes;

    private Integer position;

    private ContentType contentType;
}
