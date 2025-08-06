package com.shifterwebapp.shifter.course.dto;

import com.shifterwebapp.shifter.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoPreview {

    private Long id;

    private String imageUrl;

    private String color;

    private String titleShort;

    private String title;

    private Difficulty difficulty;

    private Integer durationMinutes;

    private Double price;

    private Integer rating;

    private Integer ratingCount;

    private List<String> skillsGained;

    private List<String> topicsCovered;

    private Integer courseContentCount;
}

