package com.shifterwebapp.shifter.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "course_sequence", allocationSize = 1)
    private Integer id;
    
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
    
    @ElementCollection
    private List<String> skillsGained;

    @ElementCollection
    private List<String> whatWillBeLearned;
    
    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private List<CourseContent> courseContents;
}

enum Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}