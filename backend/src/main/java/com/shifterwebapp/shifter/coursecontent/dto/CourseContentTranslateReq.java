package com.shifterwebapp.shifter.coursecontent.dto;

import com.shifterwebapp.shifter.courselecture.dto.CourseLectureTranslateReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseContentTranslateReq {

    private Long id;

    private String title;

    private List<CourseLectureTranslateReq> courseLectures;

}
