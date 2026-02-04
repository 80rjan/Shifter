package com.shifterwebapp.shifter.course.course;

import com.shifterwebapp.shifter.account.expert.Expert;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.course.coursetranslate.CourseTranslate;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "idx_course_difficulty", columnList = "difficulty"),
        @Index(name = "idx_course_price", columnList = "price"),
        @Index(name = "idx_course_duration", columnList = "durationMinutes"),
        @Index(name = "idx_course_price_difficulty_duration", columnList = "price, difficulty, durationMinutes")
})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String imageUrl;

    private String color;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    // when course is saved, courseVersion is saved
    private List<CourseVersion> courseVersions;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    // when course is deleted, courseContents are deleted. Delete courseContents from db if courseContent=null
    private List<CourseTranslate> translations;

    @ManyToMany
    @JoinTable(
            name = "course_tag",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "expert_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "expert_id")
    )
    private List<Expert> experts;

}

