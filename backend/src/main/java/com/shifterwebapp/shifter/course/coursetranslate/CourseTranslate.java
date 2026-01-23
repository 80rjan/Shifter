package com.shifterwebapp.shifter.course.coursetranslate;

import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.enums.Language;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseTranslate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language language;

    private String titleShort;

    private String title;

    private String descriptionShort;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String descriptionLong;

    @ElementCollection
    @Column(columnDefinition = "text")
    private List<String> whatWillBeLearned;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
