package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.enums.Language;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_content_translate_course_content_language",
                        columnNames = {"course_content_id", "language"}        // each course content can have only one translation per language
                )
        })
public class CourseContentTranslate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "course_content_id", nullable = false)
    private CourseContent courseContent;
}
