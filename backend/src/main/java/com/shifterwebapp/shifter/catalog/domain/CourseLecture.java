package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.catalog.domain.enums.CourseLectureContentType;
import com.shifterwebapp.shifter.learning.domain.LectureProgress;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: add indexes
public class CourseLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Integer position;                   // todo: add unique index for pair (course_module, position)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLectureContentType contentType;

    @OneToMany(mappedBy = "courseLecture", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<CourseLectureTranslate> translations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_module_id", nullable = false)
    private CourseModule courseModule;

    @OneToMany(mappedBy = "courseLecture", cascade = {}, fetch = FetchType.LAZY)
    private List<LectureProgress> lectureProgresses;
}

