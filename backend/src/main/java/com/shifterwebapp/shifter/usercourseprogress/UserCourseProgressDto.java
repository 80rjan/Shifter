package com.shifterwebapp.shifter.usercourseprogress;

import com.shifterwebapp.shifter.coursecontent.CourseContentDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseProgressDto {

    private Long id;

    private CourseContentDto courseContent;

    private boolean completed;

}
