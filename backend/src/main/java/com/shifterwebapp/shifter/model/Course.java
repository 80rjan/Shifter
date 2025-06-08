package com.shifterwebapp.shifter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Course {
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
    private ArrayList<String> skillsGained;
    private ArrayList<String> whatWillBeLearned;
    private ArrayList<CourseContent> modules;
}

enum Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}