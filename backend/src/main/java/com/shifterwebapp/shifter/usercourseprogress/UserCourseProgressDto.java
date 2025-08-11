package com.shifterwebapp.shifter.usercourseprogress;

import com.shifterwebapp.shifter.coursecontent.CourseContentDtoPreview;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseProgressDto {

    private Long id;

    private boolean completed;

    private LocalDateTime completedAt;
}
