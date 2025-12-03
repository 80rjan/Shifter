package com.shifterwebapp.shifter.course.dto;

import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoPreview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoDetail extends CourseDtoPreview  {

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> whatWillBeLearned;

    private List<CourseContentDtoPreview> courseContents;
}

