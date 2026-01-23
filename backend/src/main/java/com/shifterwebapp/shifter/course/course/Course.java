package com.shifterwebapp.shifter.course.course;

import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.course.coursetranslate.CourseTranslate;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
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

    @OneToMany(mappedBy = "course")
    // when course is saved, courseVersion is saved
    private List<CourseVersion> courseVersions;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    // when course is deleted, courseContents are deleted. Delete courseContents from db if courseContent=null
    private List<CourseTranslate> translations;

    @ManyToMany
    @JoinTable(
            name = "course_attribute",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private List<Attribute> attributes;

}

