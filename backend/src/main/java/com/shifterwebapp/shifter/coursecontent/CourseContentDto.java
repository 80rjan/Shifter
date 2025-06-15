package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentDto {

    private Long id;

    private String title;

    private Integer position;

    private String contentURL;

    private ContentType contentType;

    private Integer courseId;
}

