package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.courselecture.CourseLecturePreviewDto;
import com.shifterwebapp.shifter.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentDto {

    private String title;

    private Integer position;

    private List<CourseLecturePreviewDto> courseLectures;
}

