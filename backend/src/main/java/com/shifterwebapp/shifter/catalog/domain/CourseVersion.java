package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.learning.domain.Enrollment;
import com.shifterwebapp.shifter.assessment.domain.Quiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO: add indexes
public class CourseVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Version number cannot be null")
    @Column(nullable = false)
    private Integer versionNumber;

    @NotNull(message = "Creation date cannot be null")
    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private boolean active;

    @Builder.Default
    @OneToMany(mappedBy = "courseVersion", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    @OrderBy("position ASC")
    private List<CourseModule> courseModules = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Builder.Default
    @OneToMany(mappedBy = "courseVersion", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToOne(mappedBy = "courseVersion", fetch = FetchType.EAGER)
    private Quiz quiz;

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDate.now();
    }
}
