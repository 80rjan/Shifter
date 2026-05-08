package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.assessment.domain.Quiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: add indexes
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Position cannot be null")
    @Column(nullable = false)
    private Integer position;                       // todo: add unique index for pair (course_version, position)

    @OneToMany(mappedBy = "courseModule", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    @OrderBy("position ASC")
    private List<CourseLecture> courseLectures;

    @OneToMany(mappedBy = "courseModule", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<CourseModuleTranslation> translations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_version_id", nullable = false)
    private CourseVersion courseVersion;

    @OneToOne(mappedBy = "courseModule", fetch = FetchType.EAGER)
    private Quiz quiz;
}

