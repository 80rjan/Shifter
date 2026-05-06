package com.shifterwebapp.shifter.learning.domain;

import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
import com.shifterwebapp.shifter.learning.domain.Enrollment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
// TODO: add indexes when creating admin dashboard to track user progress efficiently
// todo: indexes
public class LectureProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    //---------MAPPINGS---------
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
