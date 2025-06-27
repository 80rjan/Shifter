package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enums.Skills;
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
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
//    @SequenceGenerator(name = "course_seq", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String color;
    
    private String title;
    
    private String topic;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    
    private Float durationHours;
    
    private Float price;
    
    private Integer rating;
    
    private Integer ratingCount;
    
    private String descriptionShort;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String descriptionLong;


    @ElementCollection(targetClass = Skills.class)
    @Enumerated(EnumType.STRING)
    private List<Skills> skillsGained;

    @ElementCollection
    private List<String> whatWillBeLearned;
    
    @OneToMany(mappedBy = "course", orphanRemoval = true)        // IS THIS GOOD BUSINESS LOGIC? SHOULD I HAVE CASCADES?
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseContent> courseContents;
}

