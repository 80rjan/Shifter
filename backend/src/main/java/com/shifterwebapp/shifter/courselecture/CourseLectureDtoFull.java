package com.shifterwebapp.shifter.courselecture;

import com.shifterwebapp.shifter.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLectureDtoFull {

    private Long id;

    private String title;

    private String description;

    private Integer durationMinutes;

    private Integer position;

    private String contentText;

    private String contentStoragePath;

    private ContentType contentType;
}
