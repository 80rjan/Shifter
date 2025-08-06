package com.shifterwebapp.shifter.course.dto;

import com.shifterwebapp.shifter.coursecontent.CourseContentDtoFull;
import com.shifterwebapp.shifter.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoFull {

    private Long id;

    private String imageUrl;

    private String color;

    private String titleShort;

    private String title;

    private Difficulty difficulty;

    private Integer durationMinutes;

    private Double price;

    private List<String> skillsGained;

    private List<String> topicsCovered;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> whatWillBeLearned;

    // NEW FOR FULL DTO

    private List<CourseContentDtoFull> courseContents;
}
