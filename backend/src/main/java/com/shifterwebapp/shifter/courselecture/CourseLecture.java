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
public class CourseLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer durationMinutes;

    private Integer position;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "course_content_id")
    private CourseContent courseContent;

    @OneToMany(mappedBy = "courseLecture", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserCourseProgress> userCourseProgress;

    @OneToMany(mappedBy = "courseLecture", cascade = CascadeType.ALL, orphanRemoval = true)
    // when course lecture is deleted, translations are deleted. When translations are removed from the list, they are deleted.
    private List<CourseLectureTranslate> translations;
}

