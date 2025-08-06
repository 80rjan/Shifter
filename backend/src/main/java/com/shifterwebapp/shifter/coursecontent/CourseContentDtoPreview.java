package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.courselecture.CourseLectureDtoPreview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentDtoPreview {

    private String title;

    private Integer position;

    private List<CourseLectureDtoPreview> courseLectures;
}

