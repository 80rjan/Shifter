package com.shifterwebapp.shifter.courselecture;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import jakarta.persistence.*;
import com.shifterwebapp.shifter.enums.ContentType;
import lombok.*;

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

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private Integer durationMinutes;

    private Integer position;

    @Column(columnDefinition = "text")
    private String contentText;

    @Column(columnDefinition = "text")
    private String contentFileName;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "course_content_id")
    private CourseContent courseContent;

    @OneToMany(mappedBy = "courseLecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCourseProgress> userCourseProgressList;
}

