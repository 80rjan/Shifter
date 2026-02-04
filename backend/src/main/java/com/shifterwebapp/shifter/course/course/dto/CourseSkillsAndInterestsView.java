package com.shifterwebapp.shifter.course.course.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CourseSkillsAndInterestsView {
    Long courseId;
    List<String> skills;
    List<String> interests;
}
