package com.shifterwebapp.shifter.catalog.web.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CourseSkillsAndTopicsResponse {
    Long courseId;
    List<String> skills;
    List<String> interests;
}
