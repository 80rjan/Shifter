package com.shifterwebapp.shifter.course.coursetranslate.dto;

import com.shifterwebapp.shifter.tag.dto.TagReqShort;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentTranslateReq;
import com.shifterwebapp.shifter.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTranslateReq {

    private Long id;

    private Language language;

    private String titleShort;

    private String title;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> whatWillBeLearned;

    private List<TagReqShort> skillsGained;

    private List<TagReqShort> topicsCovered;

    private List<CourseContentTranslateReq> courseContents;
}
