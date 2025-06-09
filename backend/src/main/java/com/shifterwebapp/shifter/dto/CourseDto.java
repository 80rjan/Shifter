package com.shifterwebapp.shifter.dto;

import com.shifterwebapp.shifter.model.CourseContent;
import com.shifterwebapp.shifter.model.Enrollment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private String title;

    private String topic;

    private Difficulty difficulty;

    private Float durationHours;

    private Float price;

    private Float rating;

    private Integer ratingCount;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> skillsGained;

    private List<String> whatWillBeLearned;

    private List<CourseContentDto> courseContents;
}

enum Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}
