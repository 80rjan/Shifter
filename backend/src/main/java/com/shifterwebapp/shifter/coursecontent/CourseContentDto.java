package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.coursecontent.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentDto {

    private String title;

    private Integer position;

    private String contentURL;

    private ContentType contentType;

    private Integer courseId;
}

