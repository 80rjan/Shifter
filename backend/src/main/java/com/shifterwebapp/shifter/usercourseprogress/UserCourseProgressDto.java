package com.shifterwebapp.shifter.usercourseprogress;

import com.shifterwebapp.shifter.coursecontent.CourseContentDtoPreview;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseProgressDto {

    private Long id;

    private CourseContentDtoPreview courseContent;

    private boolean completed;

}
