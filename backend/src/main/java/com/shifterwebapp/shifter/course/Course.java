package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.enrollment.Enrollment;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String imageUrl;

    private String color;
    
    private String titleShort;

    private String title;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Integer durationMinutes;
    
    private Double price;
    
    private Integer rating;
    
    private Integer ratingCount;
    
    private String descriptionShort;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String descriptionLong;

    @ElementCollection
    @Column(columnDefinition = "text")
    private List<String> whatWillBeLearned;

    @ElementCollection
    @Column(columnDefinition = "text")
    private List<String> skillsGained;

    @ElementCollection
    @Column(columnDefinition = "text")
    private List<String> topicsCovered;
    
    @OneToMany(mappedBy = "course", orphanRemoval = true)        // IS THIS GOOD BUSINESS LOGIC? SHOULD I HAVE CASCADES?
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseContent> courseContents;
}

