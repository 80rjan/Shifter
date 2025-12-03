package com.shifterwebapp.shifter.coursecontent.dto;

import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoLearn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentDtoLearn {

    private Long id;

    private String title;

    private Integer position;

    private List<CourseLectureDtoLearn> courseLectures;
}
