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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_translate_course_language",
                        columnNames = {"course_id", "language"}        // each course can have only one translation per language
                )
        })
public class CourseTranslate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(nullable = false)
    private String titleShort;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String descriptionShort;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(columnDefinition = "text", nullable = false)
    private String descriptionLong;

    @ElementCollection
    @Column(columnDefinition = "text")
    private List<String> whatWillBeLearned;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
