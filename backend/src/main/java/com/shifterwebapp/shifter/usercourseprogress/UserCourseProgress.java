package com.shifterwebapp.shifter.usercourseprogress;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class UserCourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean completed;

    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "course_lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CourseLecture courseLecture;

    public Long getCourseLectureId() {
        return courseLecture != null ? courseLecture.getId() : null;
    }
}
