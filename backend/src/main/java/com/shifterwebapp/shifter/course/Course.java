package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Integer durationMinutes;
    
    private Double price;
    
    @OneToMany(mappedBy = "course", orphanRemoval = true)        // IS THIS GOOD BUSINESS LOGIC? SHOULD I HAVE CASCADES?
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<CourseContent> courseContents;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseTranslate> courseTranslates;

    @ManyToMany
    @JoinTable(
            name = "course_attribute",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Attribute> attributes;

}

