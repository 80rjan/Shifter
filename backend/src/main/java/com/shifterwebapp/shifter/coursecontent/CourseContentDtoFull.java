package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.courselecture.CourseLectureDtoFull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentDtoFull {

    private Long id;

    private String title;

    private Integer position;

    private List<CourseLectureDtoFull> courseLectures;
}
