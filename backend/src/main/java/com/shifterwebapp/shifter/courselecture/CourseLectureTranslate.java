package com.shifterwebapp.shifter.courselecture;

import com.shifterwebapp.shifter.enums.Language;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_lecture_translate_lecture_language",
                        columnNames = {"course_lecture_id", "language"}        // each lecture can have only one translation per language
                )
        }
)
public class CourseLectureTranslate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(columnDefinition = "text")
    private String contentFileName;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(columnDefinition = "text")
    private String contentText;

    @ManyToOne
    @JoinColumn(name = "course_lecture_id", nullable = false)
    private CourseLecture courseLecture;
}
