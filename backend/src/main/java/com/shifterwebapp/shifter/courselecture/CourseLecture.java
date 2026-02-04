package com.shifterwebapp.shifter.courselecture;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import jakarta.persistence.*;
import com.shifterwebapp.shifter.enums.ContentType;
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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_lecture_course_content_position",
                        columnNames = {"course_content_id", "position"}        // each course content can have only one lecture with the same position
                )
        })
public class CourseLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "course_content_id", nullable = false)
    private CourseContent courseContent;

    @OneToMany(mappedBy = "courseLecture", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserCourseProgress> userCourseProgress;

    @OneToMany(mappedBy = "courseLecture", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    // when course lecture is deleted, translations are deleted. When translations are removed from the list, they are deleted.
    private List<CourseLectureTranslate> translations;
}

