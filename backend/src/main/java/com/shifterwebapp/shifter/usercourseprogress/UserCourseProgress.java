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
// TODO: add indexes when creating admin dashboard to track user progress efficiently
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_progress_enrollment_lecture",
                        columnNames = {"enrollment_id", "course_lecture_id"}    // each enrollment can have only one progress record per lecture
                )
        },
        indexes = {
                @Index(name = "idx_progress_enrollment_completed", columnList = "enrollment_id, completed")
        }
)
public class UserCourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "course_lecture_id", nullable = false)
    private CourseLecture courseLecture;

    public Long getCourseLectureId() {
        return courseLecture != null ? courseLecture.getId() : null;
    }
}
