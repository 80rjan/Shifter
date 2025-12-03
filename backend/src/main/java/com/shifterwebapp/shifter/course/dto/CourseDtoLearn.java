package com.shifterwebapp.shifter.course.dto;

import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoLearn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoLearn {

    private Long id;

    private String titleShort;

    private String title;

    private Integer rating;

    private Boolean isFinished;

    private List<CourseContentDtoLearn> courseContents;
}
