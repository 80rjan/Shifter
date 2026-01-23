package com.shifterwebapp.shifter.course.course.dto;

import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoFull;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoFull {
    // Course entity
    private Long id;

    private String imageUrl;

    private String color;

    private Difficulty difficulty;

    private Double price;

    private Integer durationMinutes;

    // Language specific
    private Language language;

    private String titleShort;

    private String title;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> whatWillBeLearned;

    private List<String> skillsGained;

    private List<String> topicsCovered;

    private List<CourseContentDtoFull> courseContents;

}
