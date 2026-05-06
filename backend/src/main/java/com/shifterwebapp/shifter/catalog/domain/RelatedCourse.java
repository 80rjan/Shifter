package com.shifterwebapp.shifter.catalog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: add unique constraint for pair (course_id, related_course_id) and check that course_id < related_course_id to avoid duplicates
public class RelatedCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal similarityScore;

    private LocalDateTime calculatedAt;

    @ManyToOne(fetch = FetchType.LAZY)      // todo: fetch type?
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;              // ALWAYS SMALLER ID

    @ManyToOne(fetch = FetchType.LAZY)      // todo: fetch type?
    @JoinColumn(name = "related_course_id", nullable = false)
    private Course relatedToCourse;       // ALWAYS BIGGER ID

    @PrePersist
    protected void onCreate() {
        calculatedAt = LocalDateTime.now();
    }
}
