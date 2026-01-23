package com.shifterwebapp.shifter.course.coursetranslate.dto;

import com.shifterwebapp.shifter.attribute.dto.AttributeReqShort;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoTranslate;
import com.shifterwebapp.shifter.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoTranslate {

    private Long id;

    private Language language;

    private String titleShort;

    private String title;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> whatWillBeLearned;

    private List<AttributeReqShort> skillsGained;

    private List<AttributeReqShort> topicsCovered;

    private List<CourseContentDtoTranslate> courseContents;
}
