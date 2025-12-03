package com.shifterwebapp.shifter.coursecontent.dto;

import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoTranslate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseContentDtoTranslate {

    private Long id;

    private String title;

    private List<CourseLectureDtoTranslate> courseLectures;

}
